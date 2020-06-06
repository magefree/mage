package mage.cards.l;

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
public final class LilianaDeathMage extends CardImpl {

    public LilianaDeathMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{B}{B}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.LILIANA);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(4));

        // +1: Return up to one target creature card from your graveyard to your hand.
        // −3: Destroy target creature. Its controller loses 2 life.
        // −7: Target opponent loses 2 life for each creature card in their graveyard.
    }

    private LilianaDeathMage(final LilianaDeathMage card) {
        super(card);
    }

    @Override
    public LilianaDeathMage copy() {
        return new LilianaDeathMage(this);
    }
}
