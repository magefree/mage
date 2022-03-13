package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ToweringTitan extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledCreaturePermanent("a creature with defender");

    static {
        filter.add(new AbilityPredicate(DefenderAbility.class));
    }

    public ToweringTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Towering Titan enters the battlefield with X +1/+1 counters on it, where X is the total toughness of other creatures you control.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(), ToweringTitanCount.instance, false
        ), "with X +1/+1 counters on it, where X is the total toughness of other creatures you control"));

        // Sacrifice a creature with defender: All creatures gain trample until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new GainAbilityAllEffect(
                        TrampleAbility.getInstance(), Duration.EndOfTurn,
                        StaticFilters.FILTER_PERMANENT_CREATURE
                ).setText("All creatures gain trample until end of turn"),
                new SacrificeTargetCost(new TargetControlledPermanent(filter))
        ));
    }

    private ToweringTitan(final ToweringTitan card) {
        super(card);
    }

    @Override
    public ToweringTitan copy() {
        return new ToweringTitan(this);
    }
}

enum ToweringTitanCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE,
                        sourceAbility.getControllerId(), sourceAbility, game
                ).stream()
                .filter(Objects::nonNull)
                .map(MageObject::getToughness)
                .mapToInt(MageInt::getValue)
                .sum();
    }

    @Override
    public ToweringTitanCount copy() {
        return null;
    }

    @Override
    public String getMessage() {
        return "";
    }
}