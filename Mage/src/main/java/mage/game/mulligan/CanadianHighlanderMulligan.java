package mage.game.mulligan;

import mage.game.Game;
import mage.players.Player;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class CanadianHighlanderMulligan extends VancouverMulligan {

    protected Map<UUID, String> usedMulligans = new LinkedHashMap<>();

    public CanadianHighlanderMulligan(int freeMulligans) {
        super(freeMulligans);
    }

    CanadianHighlanderMulligan(final CanadianHighlanderMulligan mulligan) {
        super(mulligan);

        this.usedMulligans.putAll(mulligan.usedMulligans);
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
    public int mulliganDownTo(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
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
    public void mulligan(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        int numCards = player.getHand().size();
        int numToMulliganTo = numCards;
        player.getLibrary().addAll(player.getHand().getCards(game), game);
        player.getHand().clear();
        player.shuffleLibrary(null, game);
        if (usedMulligans != null) {
            String mulliganCode = "7";
            if (usedMulligans.containsKey(player.getId())) {
                mulliganCode = usedMulligans.get(player.getId());
            }
            numToMulliganTo = getNextMulliganNum(mulliganCode);
            usedMulligans.put(player.getId(), getNextMulligan(mulliganCode));
        }
        game.fireInformEvent(new StringBuilder(player.getLogName())
                .append(" mulligans to ")
                .append(numToMulliganTo)
                .append(numToMulliganTo == 1 ? " card" : " cards").toString());
        player.drawCards(numToMulliganTo, null, game);
    }

    @Override
    public CanadianHighlanderMulligan copy() {
        return new CanadianHighlanderMulligan(this);
    }
}
