
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.PhaseStep;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author fireshoes
 */
public final class IcatianMoneychanger extends CardImpl {

    public IcatianMoneychanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Icatian Moneychanger enters the battlefield with three credit counters on it.
        Effect effect = new AddCountersSourceEffect(CounterType.CREDIT.createInstance(3));
        effect.setText("with three credit counters on it");
        this.addAbility(new EntersBattlefieldAbility(effect));
        
        // When Icatian Moneychanger enters the battlefield, it deals 3 damage to you.
        effect = new DamageControllerEffect(3);
        effect.setText("it deals 3 damage to you");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, false));
        
        // At the beginning of your upkeep, put a credit counter on Icatian Moneychanger.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.CREDIT.createInstance()), TargetController.YOU, false));
        
        // Sacrifice Icatian Moneychanger: You gain 1 life for each credit counter on Icatian Moneychanger. Activate this ability only during your upkeep.
        this.addAbility(new ConditionalActivatedAbility(Zone.BATTLEFIELD, 
                new GainLifeEffect(new CountersSourceCount(CounterType.CREDIT)), new SacrificeSourceCost(), new IsStepCondition(PhaseStep.UPKEEP)));
    }

    private IcatianMoneychanger(final IcatianMoneychanger card) {
        super(card);
    }

    @Override
    public IcatianMoneychanger copy() {
        return new IcatianMoneychanger(this);
    }
}
