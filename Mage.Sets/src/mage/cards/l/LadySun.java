
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnBeforeAttackersDeclaredCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class LadySun extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature");
    static {
        filter.add(AnotherPredicate.instance);
    }

    public LadySun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Return Lady Sun and another target creature to their owners' hands. Activate this ability only during your turn, before attackers are declared.
        Effect effect = new ReturnToHandSourceEffect(true);
        effect.setText("Return Lady Sun");        
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, 
               effect, new TapSourceCost(), MyTurnBeforeAttackersDeclaredCondition.instance);
        effect = new ReturnToHandTargetEffect();
        effect.setText("and another target creature to their owners' hands");
        ability.addTarget(new TargetCreaturePermanent(filter));
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private LadySun(final LadySun card) {
        super(card);
    }

    @Override
    public LadySun copy() {
        return new LadySun(this);
    }
}
