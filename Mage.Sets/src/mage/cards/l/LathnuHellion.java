
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author cg5
 */
public final class LathnuHellion extends CardImpl {

    public LathnuHellion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HELLION);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // When Lathnu Hellion enters the battlefield, you get {E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(2)));
        
        // At the beginning of your end step, sacrifice Lathnu Hellion unless you pay {E}{E}.
        Cost cost = new PayEnergyCost(2);
        cost.setText("{E}{E}");
        Effect effect = new SacrificeSourceUnlessPaysEffect(cost);
        this.addAbility(new BeginningOfEndStepTriggeredAbility(effect, TargetController.YOU, false));
    }

    private LathnuHellion(final LathnuHellion card) {
        super(card);
    }

    @Override
    public LathnuHellion copy() {
        return new LathnuHellion(this);
    }
}
