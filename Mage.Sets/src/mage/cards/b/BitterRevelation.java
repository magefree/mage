package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author emerald000
 */
public final class BitterRevelation extends CardImpl {

    public BitterRevelation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Look at the top four cards of your library. Put two of them into your hand and the rest into your graveyard. You lose 2 life.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(4, 2, PutCards.HAND, PutCards.GRAVEYARD));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2));
    }

    private BitterRevelation(final BitterRevelation card) {
        super(card);
    }

    @Override
    public BitterRevelation copy() {
        return new BitterRevelation(this);
    }
}
