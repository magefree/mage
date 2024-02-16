package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;

/**
 *
 * @author awjackson
 */
public final class DarkBargain extends CardImpl {

    public DarkBargain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Look at the top three cards of your library. Put two of them into your hand and the other into your graveyard.
        // Dark Bargain deals 2 damage to you.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(3, 2, PutCards.HAND, PutCards.GRAVEYARD));
        this.getSpellAbility().addEffect(new DamageControllerEffect(2));
    }

    private DarkBargain(final DarkBargain card) {
        super(card);
    }

    @Override
    public DarkBargain copy() {
        return new DarkBargain(this);
    }
}
