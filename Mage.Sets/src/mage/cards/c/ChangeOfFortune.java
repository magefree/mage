package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
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
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(ChangeOfFortuneValue.instance).concatBy(", then"));
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

enum ChangeOfFortuneValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return DiscardedCardWatcher.getDiscarded(sourceAbility.getControllerId(), game);
    }

    @Override
    public ChangeOfFortuneValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "card you've discarded this turn";
    }

    @Override
    public String toString() {
        return "1";
    }
}
