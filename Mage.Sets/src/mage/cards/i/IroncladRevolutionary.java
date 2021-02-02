
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class IroncladRevolutionary extends CardImpl {

    public IroncladRevolutionary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.AETHERBORN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Ironclad Revolutionary enters the battlefield, you may sacrifice an artifact. If you do, put two +1/+1 counters on Ironclad Revolutionary and each opponent loses 2 life.
        DoIfCostPaid doEffect = new DoIfCostPaid(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2), true),
                new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledArtifactPermanent("an artifact"))));
        Effect effect = new LoseLifeOpponentsEffect(2);
        effect.setText("and each opponent loses 2 life");
        doEffect.addEffect(effect);
        this.addAbility(new EntersBattlefieldTriggeredAbility(doEffect, false));
    }

    private IroncladRevolutionary(final IroncladRevolutionary card) {
        super(card);
    }

    @Override
    public IroncladRevolutionary copy() {
        return new IroncladRevolutionary(this);
    }
}
