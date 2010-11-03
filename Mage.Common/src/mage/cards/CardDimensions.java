/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 * 
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 * 
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 * 
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package mage.cards;

import static mage.constants.Constants.*;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CardDimensions {

	public int frameHeight;
	public int frameWidth;
	public int symbolHeight;
	public int symbolWidth;
	public int contentXOffset;
	public int nameYOffset;
	public int typeYOffset;
	public int textYOffset;
	public int textWidth;
	public int textHeight;
	public int powBoxTextTop;
	public int powBoxTextLeft;
	public int nameFontSize;

	public CardDimensions(double scaleFactor) {
		frameHeight = (int)(FRAME_MAX_HEIGHT * scaleFactor);
		frameWidth = (int)(FRAME_MAX_WIDTH * scaleFactor);
		symbolHeight = (int)(SYMBOL_MAX_HEIGHT * scaleFactor);
		symbolWidth = (int)(SYMBOL_MAX_WIDTH * scaleFactor);
		contentXOffset = (int)(CONTENT_MAX_XOFFSET * scaleFactor);
		nameYOffset = (int)(NAME_MAX_YOFFSET * scaleFactor);
		typeYOffset = (int)(TYPE_MAX_YOFFSET * scaleFactor);
		textYOffset = (int)(TEXT_MAX_YOFFSET * scaleFactor);
		textWidth = (int)(TEXT_MAX_WIDTH * scaleFactor);
		textHeight = (int)(TEXT_MAX_HEIGHT * scaleFactor);
		powBoxTextTop = (int)(POWBOX_TEXT_MAX_TOP * scaleFactor);
		powBoxTextLeft = (int)(POWBOX_TEXT_MAX_LEFT * scaleFactor);
		nameFontSize = Math.max(9, (int)(NAME_FONT_MAX_SIZE * scaleFactor));
	}

}
