package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class PathOfBravery extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    static final String rule = "As long as your life total is greater than or equal to your starting life total, creatures you control get +1/+1";
    private static final DynamicValue xValue = new AttackingCreatureCount();

    public PathOfBravery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // As long as your life total is greater than or equal to your starting life total, creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(new BoostAllEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        ), LifeCondition.instance, rule)));

        // Whenever one or more creatures you control attack, you gain life equal to the number of attacking creatures.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new GainLifeEffect(
                xValue, "you gain life equal to the number of attacking creatures"
        ), 1).setTriggerPhrase("Whenever one or more creatures you control attack, "));
    }

    private PathOfBravery(final PathOfBravery card) {
        super(card);
    }

    @Override
    public PathOfBravery copy() {
        return new PathOfBravery(this);
    }
}

enum LifeCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            return you.getLife() >= game.getStartingLife();
        }
        return false;
    }
}
