
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnBeforeAttackersDeclaredCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class ZhugeJinWuStrategist extends CardImpl {

    public ZhugeJinWuStrategist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.ADVISOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Target creature can't be blocked this turn. Activate this ability only during your turn, before attackers are declared.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, 
                new CantBeBlockedTargetEffect(Duration.EndOfTurn), new TapSourceCost(), MyTurnBeforeAttackersDeclaredCondition.instance);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ZhugeJinWuStrategist(final ZhugeJinWuStrategist card) {
        super(card);
    }

    @Override
    public ZhugeJinWuStrategist copy() {
        return new ZhugeJinWuStrategist(this);
    }
}
