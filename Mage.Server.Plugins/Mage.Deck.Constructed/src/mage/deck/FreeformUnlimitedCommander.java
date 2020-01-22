package mage.deck;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Deck;

public class FreeformUnlimitedCommander extends FreeformCommander {

    public FreeformUnlimitedCommander() {
        this("Freeform Unlimited Commander");
    }

    public FreeformUnlimitedCommander(String name) {
        super(name);
        for (ExpansionSet set : Sets.getInstance().values()) {
            setCodes.add(set.getCode());
        }

        // no banned cards
        this.banned.clear();
    }

    @Override
    public int getDeckMinSize() {
        return 0;
    }

    @Override
    public int getSideboardMinSize() {
        return 0;
    }

    @Override
    public boolean validate(Deck deck) {
        return true;
    }
}
