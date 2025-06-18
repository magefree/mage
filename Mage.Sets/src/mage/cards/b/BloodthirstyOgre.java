package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX
 */
public final class BloodthirstyOgre extends CardImpl {

    private static final DynamicValue xValue = new SignInversionDynamicValue(new CountersSourceCount(CounterType.DEVOTION));

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.DEMON, "you control a Demon")
    );

    public BloodthirstyOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.OGRE, SubType.WARRIOR, SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {T}: Put a devotion counter on Bloodthirsty Ogre
        this.addAbility(new SimpleActivatedAbility(new AddCountersSourceEffect(CounterType.DEVOTION.createInstance()), new TapSourceCost()));

        // {T}: Target creature gets -X/-X until end of turn, where X is the number of devotion counters on Bloodthirsty Ogre. Activate this ability only if you control a Demon.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn)
                        .setText("target creature gets -X/-X until end of turn, " +
                                "where X is the number of devotion counters on {this}"),
                new TapSourceCost(), condition
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BloodthirstyOgre(final BloodthirstyOgre card) {
        super(card);
    }

    @Override
    public BloodthirstyOgre copy() {
        return new BloodthirstyOgre(this);
    }
}
