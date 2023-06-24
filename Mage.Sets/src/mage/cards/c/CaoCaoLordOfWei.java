
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnBeforeAttackersDeclaredCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class CaoCaoLordOfWei extends CardImpl {

    public CaoCaoLordOfWei(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {tap}: Target opponent discards two cards. Activate this ability only during your turn, before attackers are declared.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new DiscardTargetEffect(2), new TapSourceCost(), MyTurnBeforeAttackersDeclaredCondition.instance);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);        
    }

    private CaoCaoLordOfWei(final CaoCaoLordOfWei card) {
        super(card);
    }

    @Override
    public CaoCaoLordOfWei copy() {
        return new CaoCaoLordOfWei(this);
    }
}
