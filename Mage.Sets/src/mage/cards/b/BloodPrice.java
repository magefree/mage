package mage.cards.b;

import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodPrice extends CardImpl {

    public BloodPrice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Look at the top four cards of your library. Put two of them into your hand and the rest on the bottom of your library in any order. You lose 2 life.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(4, 2, PutCards.HAND, PutCards.BOTTOM_ANY));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2));
    }

    private BloodPrice(final BloodPrice card) {
        super(card);
    }

    @Override
    public BloodPrice copy() {
        return new BloodPrice(this);
    }
}
