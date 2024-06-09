
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class SenatorOnacondaFarr extends CardImpl {

    public SenatorOnacondaFarr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RODIAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beggining of each combat, target creature you control gets +1/+1 until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new BoostTargetEffect(1, 1, Duration.EndOfTurn), TargetController.ANY, false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private SenatorOnacondaFarr(final SenatorOnacondaFarr card) {
        super(card);
    }

    @Override
    public SenatorOnacondaFarr copy() {
        return new SenatorOnacondaFarr(this);
    }
}
