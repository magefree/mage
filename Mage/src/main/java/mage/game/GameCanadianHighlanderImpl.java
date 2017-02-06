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
package mage.game;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.game.turn.TurnMod;
import mage.players.Player;

public abstract class GameCanadianHighlanderImpl extends GameImpl {

    protected boolean startingPlayerSkipsDraw = true;
    protected Map<UUID, String> usedMulligans = new LinkedHashMap<>();

    public GameCanadianHighlanderImpl(MultiplayerAttackOption attackOption, RangeOfInfluence range, int freeMulligans, int startLife) {
        super(attackOption, range, 0, startLife);
    }

    public GameCanadianHighlanderImpl(final GameCanadianHighlanderImpl game) {
        super(game);
    }

    @Override
    protected void init(UUID choosingPlayerId) {
        super.init(choosingPlayerId);
        state.getTurnMods().add(new TurnMod(startingPlayerId, PhaseStep.DRAW));
    }

    private String getNextMulligan(String mulligan) {
        if (mulligan.equals("7")) {
            return "6a";
        } else if (mulligan.equals("6a")) {
            return "6b";
        } else if (mulligan.equals("6b")) {
            return "5a";
        } else if (mulligan.equals("5a")) {
            return "5b";
        } else if (mulligan.equals("5b")) {
            return "4a";
        } else if (mulligan.equals("4a")) {
            return "4b";
        } else if (mulligan.equals("4b")) {
            return "3a";
        } else if (mulligan.equals("3a")) {
            return "3b";
        } else if (mulligan.equals("3b")) {
            return "2a";
        } else if (mulligan.equals("2a")) {
            return "2b";
        } else if (mulligan.equals("2b")) {
            return "1a";
        } else if (mulligan.equals("1a")) {
            return "1b";
        }
        return "0";
    }

    private int getNextMulliganNum(String mulligan) {
        if (mulligan.equals("7")) {
            return 6;
        } else if (mulligan.equals("6a")) {
            return 6;
        } else if (mulligan.equals("6b")) {
            return 5;
        } else if (mulligan.equals("5a")) {
            return 5;
        } else if (mulligan.equals("5b")) {
            return 4;
        } else if (mulligan.equals("4a")) {
            return 4;
        } else if (mulligan.equals("4b")) {
            return 3;
        } else if (mulligan.equals("3a")) {
            return 3;
        } else if (mulligan.equals("3b")) {
            return 2;
        } else if (mulligan.equals("2a")) {
            return 2;
        } else if (mulligan.equals("2b")) {
            return 1;
        } else if (mulligan.equals("1a")) {
            return 1;
        }
        return 0;
    }

    @Override
    public int mulliganDownTo(UUID playerId) {
        Player player = getPlayer(playerId);
        int deduction = 1;
        int numToMulliganTo = -1;
        if (usedMulligans != null) {
            String mulliganCode = "7";
            if (usedMulligans.containsKey(player.getId())) {
                mulliganCode = usedMulligans.get(player.getId());
            }
            numToMulliganTo = getNextMulliganNum(mulliganCode);
        }
        if (numToMulliganTo == -1) {
            return player.getHand().size() - deduction;
        }
        return numToMulliganTo;
    }

    @Override
    public void mulligan(UUID playerId) {
        Player player = getPlayer(playerId);
        int numCards = player.getHand().size();
        int numToMulliganTo = numCards;
        player.getLibrary().addAll(player.getHand().getCards(this), this);
        player.getHand().clear();
        player.shuffleLibrary(null, this);
        if (usedMulligans != null) {
            String mulliganCode = "7";
            if (usedMulligans.containsKey(player.getId())) {
                mulliganCode = usedMulligans.get(player.getId());
            }
            numToMulliganTo = getNextMulliganNum(mulliganCode);
            usedMulligans.put(player.getId(), getNextMulligan(mulliganCode));
        }
        fireInformEvent(new StringBuilder(player.getLogName())
                .append(" mulligans to ")
                .append(Integer.toString(numToMulliganTo))
                .append(numToMulliganTo == 1 ? " card" : " cards").toString());
        player.drawCards(numToMulliganTo, this);
    }

    @Override
    public void endMulligan(UUID playerId) {
        super.endMulligan(playerId);
    }

    @Override
    public Set<UUID> getOpponents(UUID playerId) {
        Set<UUID> opponents = new HashSet<>();
        for (UUID opponentId : getState().getPlayersInRange(playerId, this)) {
            if (!opponentId.equals(playerId)) {
                opponents.add(opponentId);
            }
        }
        return opponents;
    }

    @Override
    public boolean isOpponent(Player player, UUID playerToCheck) {
        return !player.getId().equals(playerToCheck);
    }
}
