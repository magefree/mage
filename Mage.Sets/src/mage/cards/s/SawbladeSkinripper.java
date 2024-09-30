package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.PermanentsSacrificedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SawbladeSkinripper extends CardImpl {

    public SawbladeSkinripper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // {2}, Sacrifice another creature or enchantment: Put a +1/+1 counter on Sawblade Skinripper.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new GenericManaCost(2)
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ANOTHER_CREATURE_OR_ENCHANTMENT));
        this.addAbility(ability);

        // At the beginning of your end step, if you sacrificed one or more permanents this turn, Sawblade Skinripper deals that much damage to any target.
        ability = new BeginningOfEndStepTriggeredAbility(
                new DamageTargetEffect(SawbladeSkinripperValue.instance), TargetController.YOU,
                SawbladeSkinripperCondition.instance, false
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability, new PermanentsSacrificedWatcher());
    }

    private SawbladeSkinripper(final SawbladeSkinripper card) {
        super(card);
    }

    @Override
    public SawbladeSkinripper copy() {
        return new SawbladeSkinripper(this);
    }
}

enum SawbladeSkinripperCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return !game
                .getState()
                .getWatcher(PermanentsSacrificedWatcher.class)
                .getThisTurnSacrificedPermanents(source.getControllerId())
                .isEmpty();
    }

    @Override
    public String toString() {
        return "you sacrificed one or more permanents this turn";
    }
}

enum SawbladeSkinripperValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getState()
                .getWatcher(PermanentsSacrificedWatcher.class)
                .getThisTurnSacrificedPermanents(sourceAbility.getControllerId())
                .size();
    }

    @Override
    public SawbladeSkinripperValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "that much";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
