
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnBeforeAttackersDeclaredCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class KingsAssassin extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public KingsAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Destroy target tapped creature. Activate this ability only during your turn, before attackers are declared.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, 
                new DestroyTargetEffect(), new TapSourceCost(), MyTurnBeforeAttackersDeclaredCondition.instance);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private KingsAssassin(final KingsAssassin card) {
        super(card);
    }

    @Override
    public KingsAssassin copy() {
        return new KingsAssassin(this);
    }
}
