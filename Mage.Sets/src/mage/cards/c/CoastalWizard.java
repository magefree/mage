
package mage.cards.c;

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
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class CoastalWizard extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature");
    static {
        filter.add(AnotherPredicate.instance);
    }

    public CoastalWizard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Return Coastal Wizard and another target creature to their owners' hands. Activate this ability only during your turn, before attackers are declared.
        Effect effect = new ReturnToHandSourceEffect(true);
        effect.setText("Return Coastal Wizard");
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, 
                effect, new TapSourceCost(), MyTurnBeforeAttackersDeclaredCondition.instance);
        effect = new ReturnToHandTargetEffect();
        effect.setText("and another target creature to their owners' hands");
        ability.addTarget(new TargetCreaturePermanent(filter));
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private CoastalWizard(final CoastalWizard card) {
        super(card);
    }

    @Override
    public CoastalWizard copy() {
        return new CoastalWizard(this);
    }
}
