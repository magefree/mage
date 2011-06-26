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

import mage.cards.CardDimensions;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Config {

	private final static Logger logger = Logger.getLogger(Config.class);

	public static final String remoteServer;
	public static final String serverName;
	public static final int port;
	public static final double cardScalingFactor;
    public static final double handScalingFactor;
	public static final CardDimensions dimensions;
	
	public static final String defaultGameType;
	public static final String defaultDeckPath;
	public static final String defaultOtherPlayerIndex;
	public static final String defaultComputerName;

	static {
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(new File("config/config.properties")));
		} catch (IOException ex) {
			logger.fatal("Config error ", ex);
		}
		serverName = p.getProperty("server-name");
		port = Integer.parseInt(p.getProperty("port"));
		remoteServer = p.getProperty("remote-server");
		cardScalingFactor = Double.valueOf(p.getProperty("card-scaling-factor"));
        handScalingFactor = Double.valueOf(p.getProperty("hand-scaling-factor"));
		defaultGameType = p.getProperty("default-game-type", "Human");
		defaultDeckPath = p.getProperty("default-deck-path");
		defaultOtherPlayerIndex = p.getProperty("default-other-player-index");
		defaultComputerName = p.getProperty("default-computer-name");
		
		dimensions = new CardDimensions(cardScalingFactor);
	}

}
