/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.client.util;

import java.awt.Image;
import static mage.client.util.Constants.*;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Frames {

	public static Image Black = ImageHelper.loadImage(Config.frameResourcePath + "8 black.png", FRAME_MAX_WIDTH, FRAME_MAX_HEIGHT);
	public static Image Blue = ImageHelper.loadImage(Config.frameResourcePath + "8 blue.png", FRAME_MAX_WIDTH, FRAME_MAX_HEIGHT);
	public static Image Gold = ImageHelper.loadImage(Config.frameResourcePath + "8 gold.png", FRAME_MAX_WIDTH, FRAME_MAX_HEIGHT);
	public static Image Green = ImageHelper.loadImage(Config.frameResourcePath + "8 green.png", FRAME_MAX_WIDTH, FRAME_MAX_HEIGHT);
	public static Image Grey = ImageHelper.loadImage(Config.frameResourcePath + "8 grey.png", FRAME_MAX_WIDTH, FRAME_MAX_HEIGHT);
	public static Image Red = ImageHelper.loadImage(Config.frameResourcePath + "8 red.png", FRAME_MAX_WIDTH, FRAME_MAX_HEIGHT);
	public static Image White = ImageHelper.loadImage(Config.frameResourcePath + "8 white.png", FRAME_MAX_WIDTH, FRAME_MAX_HEIGHT);

	public static Image Forest = ImageHelper.loadImage(Config.frameResourcePath + "8 land mana green.png", FRAME_MAX_WIDTH, FRAME_MAX_HEIGHT);
	public static Image Island = ImageHelper.loadImage(Config.frameResourcePath + "8 land mana blue.png", FRAME_MAX_WIDTH, FRAME_MAX_HEIGHT);
	public static Image Mountain = ImageHelper.loadImage(Config.frameResourcePath + "8 land mana red.png", FRAME_MAX_WIDTH, FRAME_MAX_HEIGHT);
	public static Image Plains = ImageHelper.loadImage(Config.frameResourcePath + "8 land mana white.png", FRAME_MAX_WIDTH, FRAME_MAX_HEIGHT);
	public static Image Swamp = ImageHelper.loadImage(Config.frameResourcePath + "8 land mana black.png", FRAME_MAX_WIDTH, FRAME_MAX_HEIGHT);

	public static Image Effect = ImageHelper.loadImage(Config.frameResourcePath + "Effects\\effect0.png", FRAME_MAX_WIDTH, FRAME_MAX_HEIGHT);

	public static Image PowBoxLeft = ImageHelper.loadImage(Config.powerboxResourcePath + "graphic_card_powerbox_a_left.png");
	public static Image PowBoxMid = ImageHelper.loadImage(Config.powerboxResourcePath + "graphic_card_powerbox_a_middle.png");
	public static Image PowBoxRight = ImageHelper.loadImage(Config.powerboxResourcePath + "graphic_card_powerbox_a_right.png");

}
