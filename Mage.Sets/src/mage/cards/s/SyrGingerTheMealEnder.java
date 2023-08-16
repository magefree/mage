package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SyrGingerTheMealEnder extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent("another artifact you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition = new OpponentControlsPermanentCondition(StaticFilters.FILTER_PERMANENT_PLANESWALKER);

    public SyrGingerTheMealEnder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FOOD);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Syr Ginger, the Meal Ender has trample, hexproof, and haste as long as an opponent controls a planeswalker.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield),
                condition,
                "{this} has trample"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield),
                condition,
                ", hexproof"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield),
                condition,
                ", and haste as long as an opponent controls a planeswalker."
        ));

        this.addAbility(ability);

        // Whenever another artifact you control is put into a graveyard from the battlefield, put a +1/+1 counter on Syr Ginger and scry 1.
        ability = new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)),
                false, filter, false
        );
        ability.addEffect(new ScryEffect(1, false).concatBy("and"));
        this.addAbility(ability);

        // {2}, {T}, Sacrifice Syr Ginger: You gain life equal to its power.
        ability = new SimpleActivatedAbility(
                new GainLifeEffect(new SourcePermanentPowerCount())
                        .setText("you gain life equal to its power"),
                new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private SyrGingerTheMealEnder(final SyrGingerTheMealEnder card) {
        super(card);
    }

    @Override
    public SyrGingerTheMealEnder copy() {
        return new SyrGingerTheMealEnder(this);
    }
}
