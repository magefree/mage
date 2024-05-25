
package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTypeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GuideOfSouls extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("another creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public GuideOfSouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever another creature enters the battlefield under your control, you gain 1 life and get {E}.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(new GainLifeEffect(1), filter);
        ability.addEffect(new GetEnergyCountersControllerEffect(1).setText("and get {E}"));
        this.addAbility(ability);

        // Whenever you attack, you may pay {E}{E}{E}. When you do, put two +1/+1 counters and a flying counter on target attacking creature. It becomes an Angel in addition to its other types.
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(2))
                        .setText("put two +1/+1 counters"), false
        );
        reflexive.addEffect(new AddCountersTargetEffect(CounterType.FLYING.createInstance())
                .setText("and a flying counter on target attacking creature."));
        reflexive.addTarget(new TargetAttackingCreature());
        reflexive.addEffect(new BecomesCreatureTypeTargetEffect(Duration.EndOfGame, SubType.ANGEL, false)
                .setText("It becomes an Angel in addition to its other types."));
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new DoWhenCostPaid(reflexive, new PayEnergyCost(3), "Pay {E}{E}{E}?", true), 1
        ));
    }

    private GuideOfSouls(final GuideOfSouls card) {
        super(card);
    }

    @Override
    public GuideOfSouls copy() {
        return new GuideOfSouls(this);
    }
}
