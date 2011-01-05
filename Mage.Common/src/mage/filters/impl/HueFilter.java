/*
Copyright 2006 Jerry Huxtable

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package mage.filters.impl;

import mage.filters.PointFilter;

import java.awt.*;

/**
 * Point filter that changes hue of the image.
 *
 * @author nantuko
 */
public class HueFilter extends PointFilter {

	public float hue;
	private float[] hsb = new float[3];

	public HueFilter() {
		this(0);
	}

	public HueFilter(float hue) {
		this.hue = hue;
		canFilterIndexColorModel = true;
	}

    public void setHue(float hue) {
        this.hue = hue;
    }

    public float getHue() {
        return hue;
    }

	public int filterRGB(int x, int y, int rgb) {
		int a = rgb & 0xff000000;
		int r = (rgb >> 16) & 0xff;
		int g = (rgb >> 8) & 0xff;
		int b = rgb & 0xff;
		Color.RGBtoHSB(r, g, b, hsb);
		hsb[0] += hue;
		while (hsb[0] < 0) {
			hsb[0] += Math.PI*2;
        }
		rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
		return a | (rgb & 0xffffff);
	}

	public String toString() {
		return "Change HUE filter";
	}
}

