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
import java.util.HashMap;
import java.util.Map;
import static mage.constants.Constants.*;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Symbols {

	public static Map<String, Image> symbols = new HashMap<String, Image>();

	static {
		symbols.put("{B}", ImageHelper.loadImage(Config.symbolsResourcePath + "mana_black.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{U}", ImageHelper.loadImage(Config.symbolsResourcePath + "mana_blue.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{G}", ImageHelper.loadImage(Config.symbolsResourcePath + "mana_green.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{R}", ImageHelper.loadImage(Config.symbolsResourcePath + "mana_red.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{W}", ImageHelper.loadImage(Config.symbolsResourcePath + "mana_white.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));

		symbols.put("{0}", ImageHelper.loadImage(Config.symbolsResourcePath + "colorless_0.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{1}", ImageHelper.loadImage(Config.symbolsResourcePath + "colorless_1.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{2}", ImageHelper.loadImage(Config.symbolsResourcePath + "colorless_2.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{3}", ImageHelper.loadImage(Config.symbolsResourcePath + "colorless_3.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{4}", ImageHelper.loadImage(Config.symbolsResourcePath + "colorless_4.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{5}", ImageHelper.loadImage(Config.symbolsResourcePath + "colorless_5.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{6}", ImageHelper.loadImage(Config.symbolsResourcePath + "colorless_6.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{7}", ImageHelper.loadImage(Config.symbolsResourcePath + "colorless_7.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{8}", ImageHelper.loadImage(Config.symbolsResourcePath + "colorless_8.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{9}", ImageHelper.loadImage(Config.symbolsResourcePath + "colorless_9.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{10}", ImageHelper.loadImage(Config.symbolsResourcePath + "colorless_10.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{11}", ImageHelper.loadImage(Config.symbolsResourcePath + "colorless_11.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{12}", ImageHelper.loadImage(Config.symbolsResourcePath + "colorless_12.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{13}", ImageHelper.loadImage(Config.symbolsResourcePath + "colorless_13.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{14}", ImageHelper.loadImage(Config.symbolsResourcePath + "colorless_14.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{15}", ImageHelper.loadImage(Config.symbolsResourcePath + "colorless_15.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{16}", ImageHelper.loadImage(Config.symbolsResourcePath + "colorless_16.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{X}", ImageHelper.loadImage(Config.symbolsResourcePath + "colorless_x.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));

		symbols.put("{B/G}", ImageHelper.loadImage(Config.symbolsResourcePath + "black_green.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{B/R}", ImageHelper.loadImage(Config.symbolsResourcePath + "black_red.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{U/B}", ImageHelper.loadImage(Config.symbolsResourcePath + "blue_black.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{U/R}", ImageHelper.loadImage(Config.symbolsResourcePath + "blue_red.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{G/B}", ImageHelper.loadImage(Config.symbolsResourcePath + "green_blue.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{G/W}", ImageHelper.loadImage(Config.symbolsResourcePath + "green_white.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{R/G}", ImageHelper.loadImage(Config.symbolsResourcePath + "red_green.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{R/W}", ImageHelper.loadImage(Config.symbolsResourcePath + "red_white.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{W/B}", ImageHelper.loadImage(Config.symbolsResourcePath + "white_black.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{W/U}", ImageHelper.loadImage(Config.symbolsResourcePath + "white_blue.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));

		symbols.put("{2/B}", ImageHelper.loadImage(Config.symbolsResourcePath + "two_black.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{2/U}", ImageHelper.loadImage(Config.symbolsResourcePath + "two_blue.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{2/G}", ImageHelper.loadImage(Config.symbolsResourcePath + "two_green.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{2/R}", ImageHelper.loadImage(Config.symbolsResourcePath + "two_red.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));
		symbols.put("{2/W}", ImageHelper.loadImage(Config.symbolsResourcePath + "two_white.png", SYMBOL_MAX_WIDTH, SYMBOL_MAX_HEIGHT));

	}

	public static Image getSymbol(String symbol) {

		if (symbols.containsKey(symbol))
			return(symbols.get(symbol));
		return null;

	}
}
