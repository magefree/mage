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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import mage.client.cards.CardDimensions;
import mage.util.Logging;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Config {

	private final static Logger logger = Logging.getLogger(Config.class.getName());

	public static final String remoteServer;
	public static final String serverName;
	public static final int port;
	public static final String cardsResourcePath;
	public static final String frameResourcePath;
	public static final String powerboxResourcePath;
	public static final String cardArtResourcePath;
	public static final String symbolsResourcePath;
	public static final String setIconsResourcePath;
	public static final String resourcePath;
	public static final double cardScalingFactor;
	public static final boolean useResource;
	public static final CardDimensions dimensions;

	static {
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(new File("config/config.properties")));
		} catch (IOException ex) {
			logger.log(Level.SEVERE, null, ex);
		}
		serverName = p.getProperty("server-name");
		port = Integer.parseInt(p.getProperty("port"));
		remoteServer = p.getProperty("remote-server");
		cardsResourcePath = p.getProperty("cards-resource-path");
		resourcePath = p.getProperty("resource-path");
		cardScalingFactor = Double.valueOf(p.getProperty("card-scaling-factor"));
		dimensions = new CardDimensions(cardScalingFactor);
		File test = new File(cardsResourcePath);
		if (test.isDirectory()) {
			useResource = false;
			frameResourcePath = cardsResourcePath + "Frame\\";
			powerboxResourcePath = cardsResourcePath + "PowerBox\\";
			cardArtResourcePath = cardsResourcePath + "Pics\\";
			setIconsResourcePath = cardsResourcePath + "Icon\\";
			symbolsResourcePath = p.getProperty("symbols-resource-path");
		}
		else {
			useResource = true;
			frameResourcePath = resourcePath + "cards/frame/";
			powerboxResourcePath = resourcePath + "cards/powerbox/";
			cardArtResourcePath = resourcePath + "cards/art/";
			setIconsResourcePath = resourcePath + "cards/icon/";
			symbolsResourcePath = resourcePath + "symbols/";
		}
	}

}
