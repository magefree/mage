package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.filter.FilterPermanent;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author tiera3 - based on ArmorOfThorns, AkroanLineBreaker, RonomSerpent, GeneralsEnforcer, MildManneredLibrarian, BlazemireVerge, ThoughtShucker
 */
public final class GoblinSkiPatrol extends CardImpl {
    public GoblinSkiPatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{R}: Goblin Ski Patrol gets +2/+0 and gains flying. Its controller sacrifices it at the beginning of the next end step. Activate only once and only if you control a snow Mountain.
        this.addAbility(new GoblinSkiPatrolActivatedAbility());
    }

    private GoblinSkiPatrol(final GoblinSkiPatrol card) {
        super(card);
    }

    @Override
    public GoblinSkiPatrol copy() {
        return new GoblinSkiPatrol(this);
    }
}

class GoblinSkiPatrolActivatedAbility extends ConditionalActivatedAbility {
    GoblinSkiPatrolActivatedAbility() {
        super(new BoostSourceEffect(2, 0, Duration.EndOfTurn).setText("{this} gets +2/+0"),
                new ManaCostsImpl<>("{1}{R}"), 
                new PermanentsOnTheBattlefieldCondition(
                    new FilterPermanent(SubType.MOUNTAIN, "a snow-covered Mountain").add(SuperType.SNOW.getPredicate())) );
        addEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn
            ).setText("gains flying").concatBy("and"));
        addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new SacrificeSourceEffect(true))
            ).setText("Its controller sacrifices it at the beginning of the next end step."));
        maxActivationsPerGame = 1;
    }

    protected GoblinSkiPatrolActivatedAbility(final GoblinSkiPatrolActivatedAbility ability) {
        super(ability);
    }

    @Override
    public GoblinSkiPatrolActivatedAbility copy() {
        return new GoblinSkiPatrolActivatedAbility(this);
    }

    @Override
    public String getRule() {
        String rule = super.getRule();
        int len = rule.length();
        return rule.substring(0, len - 1) + " and only once.";
    }

}
