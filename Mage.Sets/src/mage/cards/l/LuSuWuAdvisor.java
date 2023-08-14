
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnBeforeAttackersDeclaredCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class LuSuWuAdvisor extends CardImpl {

    public LuSuWuAdvisor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {tap}: Draw a card. Activate this ability only during your turn, before attackers are declared.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, 
                new DrawCardSourceControllerEffect(1), new TapSourceCost(), MyTurnBeforeAttackersDeclaredCondition.instance);
        this.addAbility(ability);
    }

    private LuSuWuAdvisor(final LuSuWuAdvisor card) {
        super(card);
    }

    @Override
    public LuSuWuAdvisor copy() {
        return new LuSuWuAdvisor(this);
    }
}
