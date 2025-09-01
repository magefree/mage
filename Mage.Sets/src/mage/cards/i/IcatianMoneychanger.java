package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class IcatianMoneychanger extends CardImpl {

    public IcatianMoneychanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Icatian Moneychanger enters the battlefield with three credit counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.CREDIT.createInstance(3))
                        .setText("with three credit counters on it")
        ));

        // When Icatian Moneychanger enters the battlefield, it deals 3 damage to you.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DamageControllerEffect(3, "it")));

        // At the beginning of your upkeep, put a credit counter on Icatian Moneychanger.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.CREDIT.createInstance())));

        // Sacrifice Icatian Moneychanger: You gain 1 life for each credit counter on Icatian Moneychanger. Activate this ability only during your upkeep.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                new GainLifeEffect(new CountersSourceCount(CounterType.CREDIT))
                        .setText("you gain 1 life for each credit counter on this creature"),
                new SacrificeSourceCost(), IsStepCondition.getMyUpkeep()
        ));
    }

    private IcatianMoneychanger(final IcatianMoneychanger card) {
        super(card);
    }

    @Override
    public IcatianMoneychanger copy() {
        return new IcatianMoneychanger(this);
    }
}
