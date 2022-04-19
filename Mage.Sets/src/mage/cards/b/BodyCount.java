package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.SpectacleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.watchers.common.CreaturesDiedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BodyCount extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Creatures that died under your control this turn", BodyCountValue.instance
    );

    public BodyCount(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Spectacle {B}
        this.addAbility(new SpectacleAbility(this, new ManaCostsImpl<>("{B}")));

        // Draw a card for each creature that died under your control this turn.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(BodyCountValue.instance));
        this.getSpellAbility().addHint(hint);
    }

    private BodyCount(final BodyCount card) {
        super(card);
    }

    @Override
    public BodyCount copy() {
        return new BodyCount(this);
    }
}

enum BodyCountValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getState()
                .getWatcher(CreaturesDiedWatcher.class)
                .getAmountOfCreaturesDiedThisTurnByController(sourceAbility.getControllerId());
    }

    @Override
    public BodyCountValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "creature that died under your control this turn";
    }

    @Override
    public String toString() {
        return "1";
    }
}
