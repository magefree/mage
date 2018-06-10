
package mage.game;

import java.util.*;
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
        switch (mulligan) {
            case "7":
                return "6a";
            case "6a":
                return "6b";
            case "6b":
                return "5a";
            case "5a":
                return "5b";
            case "5b":
                return "4a";
            case "4a":
                return "4b";
            case "4b":
                return "3a";
            case "3a":
                return "3b";
            case "3b":
                return "2a";
            case "2a":
                return "2b";
            case "2b":
                return "1a";
            case "1a":
                return "1b";
        }
        return "0";
    }

    private int getNextMulliganNum(String mulligan) {
        switch (mulligan) {
            case "7":
                return 6;
            case "6a":
                return 6;
            case "6b":
                return 5;
            case "5a":
                return 5;
            case "5b":
                return 4;
            case "4a":
                return 4;
            case "4b":
                return 3;
            case "3a":
                return 3;
            case "3b":
                return 2;
            case "2a":
                return 2;
            case "2b":
                return 1;
            case "1a":
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

}
