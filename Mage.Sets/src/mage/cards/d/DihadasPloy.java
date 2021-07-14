package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.JumpStartAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.watchers.common.DiscardedCardWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DihadasPloy extends CardImpl {

    private static final Hint hint = new ValueHint("Cards you've discarded this turn", DihadasPloyValue.instance);

    public DihadasPloy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{B}");

        // Draw two cards, then discard a card. You gain life equal to the number of cards you've discarded this turn.
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(2, 1));
        this.getSpellAbility().addEffect(new GainLifeEffect(DihadasPloyValue.instance));
        this.getSpellAbility().addHint(hint);
        this.getSpellAbility().addWatcher(new DiscardedCardWatcher());

        // Jump-start
        this.addAbility(new JumpStartAbility(this));
    }

    private DihadasPloy(final DihadasPloy card) {
        super(card);
    }

    @Override
    public DihadasPloy copy() {
        return new DihadasPloy(this);
    }
}

enum DihadasPloyValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return DiscardedCardWatcher.getDiscarded(sourceAbility.getControllerId(), game);
    }

    @Override
    public DihadasPloyValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "cards you've discarded this turn";
    }
}
