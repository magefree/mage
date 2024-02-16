package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.watchers.common.CardsDrawnThisTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EvenTheScore extends CardImpl {

    public EvenTheScore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}{U}{U}");

        // This spell costs {U}{U}{U} less to cast if an opponent has drawn four or more cards this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SpellCostReductionSourceEffect(
                        new ManaCostsImpl<>("{U}{U}{U}"),
                        EvenTheScoreCondition.instance
                )
        ).setRuleAtTheTop(true));

        // Draw X cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(ManacostVariableValue.REGULAR));
    }

    private EvenTheScore(final EvenTheScore card) {
        super(card);
    }

    @Override
    public EvenTheScore copy() {
        return new EvenTheScore(this);
    }
}

enum EvenTheScoreCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getOpponents(source.getControllerId())
                .stream()
                .mapToInt(game.getState().getWatcher(CardsDrawnThisTurnWatcher.class)::getCardsDrawnThisTurn)
                .anyMatch(x -> x >= 4);
    }

    @Override
    public String toString() {
        return "an opponent has drawn four or more cards this turn";
    }
}
