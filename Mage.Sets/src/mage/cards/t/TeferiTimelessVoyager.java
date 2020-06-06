package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author htrajan
 */
public final class TeferiTimelessVoyager extends CardImpl {

    public TeferiTimelessVoyager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{U}{U}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TEFERI);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(4));

        // +1: Draw a card.
        // −3: Put target creature on top of its owner's library.
        // −8: Each creature target opponent controls phases out. Until the end of your next turn, they can't phase in.
    }

    private TeferiTimelessVoyager(final TeferiTimelessVoyager card) {
        super(card);
    }

    @Override
    public TeferiTimelessVoyager copy() {
        return new TeferiTimelessVoyager(this);
    }
}
