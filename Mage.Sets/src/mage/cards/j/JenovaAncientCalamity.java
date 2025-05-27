package mage.cards.j;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JenovaAncientCalamity extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.MUTANT, "a Mutant you control");

    public JenovaAncientCalamity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ALIEN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // At the beginning of combat on your turn, put a number of +1/+1 counters equal to Jenova's power onto up to one other target creature. That creature becomes a Mutant in addition to its other types.
        Ability ability = new BeginningOfCombatTriggeredAbility(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance(), SourcePermanentPowerValue.NOT_NEGATIVE
        ).setText("put a number of +1/+1 counters equal to {this}'s power on up to one other target creature"));
        ability.addEffect(new AddCardSubTypeTargetEffect(SubType.MUTANT, Duration.Custom)
                .setText("That creature becomes a Mutant in addition to its other types"));
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);

        // Whenever a Mutant you control dies during your turn, draw cards equal to its power.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new DrawCardSourceControllerEffect(JenovaAncientCalamityValue.instance)
                        .setText("you draw cards equal to its power"), false, filter
        ).withTriggerCondition(MyTurnCondition.instance));
    }

    private JenovaAncientCalamity(final JenovaAncientCalamity card) {
        super(card);
    }

    @Override
    public JenovaAncientCalamity copy() {
        return new JenovaAncientCalamity(this);
    }
}

enum JenovaAncientCalamityValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return CardUtil.getEffectValueFromAbility(sourceAbility, "creatureDied", Permanent.class)
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .orElse(0);
    }

    @Override
    public JenovaAncientCalamityValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}
