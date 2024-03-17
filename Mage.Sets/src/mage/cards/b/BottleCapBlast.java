package mage.cards.b;

import java.util.UUID;
import mage.abilities.keyword.ImproviseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class BottleCapBlast extends CardImpl {

    public BottleCapBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");
        

        // Improvise
        this.addAbility(new ImproviseAbility());

        // Bottle-Cap Blast deals 5 damage to any target. If excess damage was dealt to a permanent this way, create that many apped Treasure tokens.
    }

    private BottleCapBlast(final BottleCapBlast card) {
        super(card);
    }

    @Override
    public BottleCapBlast copy() {
        return new BottleCapBlast(this);
    }
}
