/*
 * This file is part of AudioToMidi, licensed under the MIT License (MIT).
 *
 * Copyright (c) TechShroom Studios <https://techshroom.com>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package me.kenzierocks.a2m.v2;

import java.nio.DoubleBuffer;

public class HC {

    public static void to_polar2(int len, DoubleBuffer freq, int conj, double scale, double[] amp2, double[] phs) {
        int i;
        double rl, im;

        double[] array = WindowHelper.getWindowingArray(len);
        freq.get(array);

        phs[0] = 0.0;
        double f0 = array[0];
        amp2[0] = f0 * f0 / scale;
        for (i = 1; i < (len + 1) / 2; i++) {
            rl = array[i];
            im = array[len - i];
            amp2[i] = (rl * rl + im * im) / scale;
            if (amp2[i] > 0.0) {
                if (conj == 0)
                    phs[i] = FastTrig.fast_atan2(+im, rl);
                else
                    phs[i] = FastTrig.fast_atan2(-im, rl);
            } else {
                phs[i] = 0.0;
            }
        }
        if (len % 2 == 0) {
            phs[len / 2] = 0.0;
            double fl2 = array[len / 2];
            amp2[len / 2] = fl2 * fl2 / scale;
        }
    }

}
