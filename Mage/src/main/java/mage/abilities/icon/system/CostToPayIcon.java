package mage.abilities.icon.system;

import mage.abilities.icon.CardIconImpl;
import mage.abilities.icon.CardIconType;
import mage.players.PlayableObjectStats;

import java.util.List;

/**
 * GUI: real cost icon, shown if card have a playable ability with modified mana cost
 *
 * @author DarioBarrabinoDev
 */
public final class CostToPayIcon extends CardIconImpl {

    public CostToPayIcon(PlayableObjectStats objectStats) {
        super(CardIconType.OTHER_COST_TO_PAY, getHint(objectStats));
    }

    private static String getHint(PlayableObjectStats objectStats) {
        String res = "Real cost to pay instead printed cost:";
        List<String> list = objectStats.getPlayableCostsToPay();
        if (!list.isEmpty()) {
            res += "<br> " + String.join("<br> ", list);
        }
        return res;
    }
}
