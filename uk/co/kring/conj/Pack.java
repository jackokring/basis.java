/*
 * (C) 2016 K Ring Technologies Ltd.
 * All right reserved.
 * A request to use this code can be sent to <jacko@kring.co.uk>.
 */
package uk.co.kring.conj;

/**
 *
 * @author jacko
 */
public class Pack {

    function encodeLZW(data, bounce) {
        var dict = {};
        data = encodeSUTF(data);
        var out = [];
        var currChar;
        var phrase = data[0];
        var codeL = 0;
        var code = 256;
        for (var i=1; i<data.length; i++) {
            currChar=data[i];
            if (dict['_' + phrase + currChar] != null) {
                phrase += currChar;
            }
            else {
                out.push(codeL = phrase.length > 1 ? dict['_'+phrase] : phrase.charCodeAt(0));
                if(code < 65536) {//limit
                    dict['_' + phrase + currChar] = code;
                    code++;
                    if(bounce && codeL != code - 2) {//code -- and one before would be last symbol out
                        _.each(phrase.split(''), function (chr) {
                            if(code < 65536) {
                                    while(dict['_' + phrase + chr]) phrase += chr;
                                    dict['_' + phrase + chr] = code;
                                    code++;
                            }
                        });
                    }
                }
                phrase=currChar;
            }
        }
        out.push(phrase.length > 1 ? dict['_'+phrase] : phrase.charCodeAt(0));
        for (var i=0; i<out.length; i++) {
            out[i] = String.fromCharCode(out[i]);
        }
        return out.join('');
    }

    function encodeSUTF(s) {
            s = mangleUTF(encodeUTF(s).split(''));
        var out = [];
        var last = 0;
        var flag = false;
        var first = true;
        _.each(s, function(val) {
            k = val.charCodeAt();
                if(k > 127) {
                    if(k & 64 == 0) {
                        if(first && flag) {
                            first = false;
                            if(k == last) {
                                return;////1st extension byte
                            } else {
                                last = k;
                                flag = false;;//1st extension 
                            }
                        } else {
                            if(flag) k += 64;
                            first = true;//extension byte
                            flag = false;
                        }
                    } else {
                            flag = (k & 32 != 0);//hi page byte (flag two extensions)
                    }
            } else {
                //ascii byte
                }
                out.push(String.fromCharCode(k));
        });
        return out;
    }

    function encodeBounce(s) {
        return encodeLZW(s, true);
    }

    //=================================================
    // Decompress an LZW-encoded string
    //=================================================
    function decodeLZW(s, bounce) {
        var dict = {};
        var dictI = {};
        var data = (s + '').split('');
        var currChar = data[0];
        var oldPhrase = currChar;
        var out = [currChar];
        var code = 256;
        var phrase;
        for (var i=1; i<data.length; i++) {
            var currCode = data[i].charCodeAt(0);
            if (currCode < 256) {
                phrase = data[i];
            }
            else {
               phrase = dict['_'+currCode] ? dict['_'+currCode] : (oldPhrase + currChar);
            }
            out.push(phrase);
            currChar = phrase.charAt(0);
            if(code < 65536) {
                dict['_'+code] = oldPhrase + currChar;
                dictI['_' + oldPhrase + currChar] = code;
                code++;
                if(bounce && !dict['_'+currCode]) {//the special lag
                    _.each(oldPhrase.split(''), function (chr) {
                        if(code < 65536) {
                            while(dictI['_' + oldPhrase + chr]) oldPhrase += chr; 
                            dict['_' + code] = oldPhrase + chr;
                            dictI['_' + oldPhrase + chr] = code;
                            code++;
                        }
                    });
                }
            }
            oldPhrase = phrase;
        }
        out = decodeSUTF(out);
        return out.join('');
    }

    function decodeSUTF(s) {
        var out = [];
        var last = 0;
        var flag = false;
        var first = true;
        _.each(s, function(val) {
            k = val.charCodeAt();
                if(k > 127) {
                    if(first) {
                        first = false;
                        flag = (k & 32 != 0);
                    } else {
                        if(k & 64 != 0 && flag) {
                            out.push(String.fromCharCode(last));
                            k -= 64;
                            first = true;
                        } else {
                            if(flag) {
                                last = k;
                                flag = false;
                            } else {
                                first = true;
                            }
                        }
                    }
            } else {
                //ascii byte
                }
                out.push(String.fromCharCode(k));
        });
        out = decodeUTF(mangleUTF(out).join(''));
        return out;
    }

    function decodeBounce(s) {
        return decodeLZW(s, true);
    }

    //=================================================
    // UTF mangling with ArrayBuffer mappings
    //=================================================
    function encodeUTF(s) {
        return unescape(encodeURIComponent(s));
    }

    function decodeUTF(s) {
        return decodeURIComponent(escape(s));
    }

    function toBuffer(str) {
            arr = encodeSUTF(str);
            var buf = new ArrayBuffer(arr.length);
            var bufView = new Uint8Array(buf);
            for (var i = 0, arrLen = arr.length; i < arrLen; i++) {
                    bufView[i] = arr[i];
            }
            return buf;
    }

    function fromBuffer(arr) {
            arr = new UInt8Array(arr);
            var buf = new Array(arr.length);
            for (var i = 0, arrLen = arr.length; i < arrLen; i++) {
                    buf[i] = arr[i];
            }
            return decodeSUTF(buf);
    }

    function mangleUTF(s) {
            var temp = 0;
            _.each(s, function(v, k) {
                    if(v & 128 == 0 || v & 32 == 0 || v & 64 == 0) return;//aux or 2 byte or ascii
                    //must be 1st of 3 byte
                    temp = v & 15;//lower nibble
                    v &= 240;//255 - 15
                    v |= s[k + 1] & 16;
                    s[k + 1] &= 240;
                    s[k + 1] |= temp;
            });
            return s;//just in case
    }

    //===============================================
    //A Burrows Wheeler Transform of strings
    //===============================================
    function encodeBWT(data) {
        var size = data.length;
        var buff = data + data;
        var idx = _.range(size).sort(function(x, y){
            for (var i = 0; i < size; i++) {
                var r = buff[x + i].charCodeAt() - buff[y + i].charCodeAt();
                if (r !== 0) return r;
            }
            return 0;
        });

        var top;
        var work = _.reduce(_.range(size), function(memo, k){
            var p = idx[k];
            if (p === 0) top = k;
            memo.push(buff[p + size - 1]);
            return memo;
        }, []).join('');

        return { top: top, data: work };
    }

    function decodeBWT(top, data) { //JSON

        var size = data.length;
        var idx = _.range(size).sort(function(x, y){
            var c = data[x].charCodeAt() - data[y].charCodeAt();
            if (c === 0) return x - y;
            return c;
        });

        var p = idx[top];
        return _.reduce(_.range(size), function(memo){
            memo.push(data[p]);
            p = idx[p];
            return memo;
        }, []).join('');
    }

    //==================================================
    // Two functions to do a dictionary effectiveness
    // split of what to compress. This has the effect
    // of applying an effective dictionary size bigger
    // than would otherwise be.
    //==================================================
    function tally(data) {
        return _.reduce(data.split(), function(memo, charAt) {
            memo[charAt]++;//increase
        }, {});
    }

    function splice(data) {
        var acc = 0;
        var counts = tally(data);
        return _.reduce(counts, function(memo, count, key) {
            memo.push(key + data.substring(acc, count + acc));
            /* adds a seek char:
            This assists in DB seek performance as it's the ordering char for the lzw block */
            acc += count;
        }, []);
    }

    //=====================================================
    // A packer and unpacker with good efficiency
    //=====================================================
    // These are the ones to call, and the rest sre maybe
    // useful, but can be considered as foundations for
    // these functions. some block length management is
    // built in.
    function pack(data) {
            //limits
            var str = JSON.stringify(data);
            var chain = {};
            if(str.length > 524288) {
                    chain = pack(str.substring(524288));
                    str = str.substring(0, 524288);
            }
        var bwt = encodeBWT(str);
        var mix = splice(bwt.data);

        mix = _.map(mix, encodeBounce);
        data = {
            top: bwt.top,
            /* tally: encode_tally(tally), */
            mix: mix,
            chn: chain
        };
        return data;
    }

    function unpack(got) {
        var top = got.top || 0;
        /* var tally = got.tally; */
        var mix = got.mix || [];

        mix = _.map(mix, decodeBounce);
        mix = _.reduce(mix, function(memo, lzw) {
            /* var key = lzw.charAt(0);//get seek char */
            memo += lzw.substring(1, lzw.length);//concat
        }, '');
        var chain = got.chn;
        var res = decodeBWT(top, mix);
            if(_.has(chain, 'chn')) {
                    res += unpack(chain.chn);
            }
        return JSON.parse(res);
    }
}