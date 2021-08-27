package mage.deck;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.DeckValidatorErrorType;
import mage.cards.decks.PennyDreadfulLegalityUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author spjspj
 */
public class PennyDreadfulCommander extends AbstractCommander {

    private static final Map<String, Integer> pdAllowed = new HashMap<>();

    public PennyDreadfulCommander() {
        super("Penny Dreadful Commander", "Penny");
        for (ExpansionSet set : Sets.getInstance().values()) {
            if (set.getSetType().isEternalLegal()) {
                setCodes.add(set.getCode());
            }
        }
    }

    @Override
    protected boolean checkBanned(Map<String, Integer> counts) {
        if (pdAllowed.isEmpty()) {
            pdAllowed.putAll(PennyDreadfulLegalityUtil.getLegalCardList());
        }
        boolean valid = true;
        for (String wantedCard : counts.keySet()) {
            if (!(pdAllowed.containsKey(wantedCard))) {
                addError(DeckValidatorErrorType.BANNED, wantedCard, "Banned", true);
                valid = false;
            }
        }
        return valid;
    }
}
