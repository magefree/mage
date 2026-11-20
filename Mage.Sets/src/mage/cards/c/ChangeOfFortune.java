package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.CardsDiscardedThisTurnCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.watchers.common.DiscardedCardWatcher;

/**
 *
 * @author weirddan455
 */
public final class ChangeOfFortune extends CardImpl {

    public ChangeOfFortune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Discard your hand, then draw a card for each card you've discarded this turn.
        this.getSpellAbility().addEffect(new DiscardHandControllerEffect());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(CardsDiscardedThisTurnCount.SINGULAR).concatBy(", then"));
        this.getSpellAbility().addHint(CardsDiscardedThisTurnCount.getHint());
        this.getSpellAbility().addWatcher(new DiscardedCardWatcher());
    }

    private ChangeOfFortune(final ChangeOfFortune card) {
        super(card);
    }

    @Override
    public ChangeOfFortune copy() {
        return new ChangeOfFortune(this);
    }
}
