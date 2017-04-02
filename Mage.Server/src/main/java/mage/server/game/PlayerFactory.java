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

package mage.server.game;

import mage.constants.RangeOfInfluence;
import mage.players.Player;
import mage.players.PlayerType;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.util.EnumMap;
import java.util.Optional;
import java.util.Set;

/**
 * @author BetaSteward_at_googlemail.com
 */
public enum PlayerFactory {

    instance;
    private static final Logger logger = Logger.getLogger(PlayerFactory.class);

    private final EnumMap<PlayerType, Class> playerTypes = new EnumMap<>(PlayerType.class);


    public Optional<Player> createPlayer(PlayerType playerType, String name, RangeOfInfluence range, int skill) {
        try {
            Class playerTypeClass = playerTypes.get(playerType);
            if (playerTypeClass != null) {
                Constructor<?> con = playerTypeClass.getConstructor(String.class, RangeOfInfluence.class, int.class);
                Player player = (Player) con.newInstance(name, range, skill);
                logger.trace("Player created: " + name + " - " + player.getId());
                return Optional.of(player);
            } else {
                logger.fatal("Unknown player type: " + playerType);
            }
        } catch (Exception ex) {
            logger.fatal("PlayerFactory error ", ex);
        }
        return Optional.empty();
    }

    public Set<PlayerType> getPlayerTypes() {
        return playerTypes.keySet();
    }

    public void addPlayerType(String name, Class playerType) {
        PlayerType type = PlayerType.getByDescription(name);
        if (type != null) {
            if (playerType != null) {
                this.playerTypes.put(type, playerType);
            }
        }
    }

}
