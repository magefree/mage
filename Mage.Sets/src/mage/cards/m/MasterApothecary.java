
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author cbt33
 */
public final class MasterApothecary extends CardImpl {
    
    static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("an untapped Cleric you control");
    
    static {
        filter.add(SubType.CLERIC.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public MasterApothecary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Tap an untapped Cleric you control: Prevent the next 2 damage that would be dealt to any target this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                                                        new PreventDamageToTargetEffect(Duration.EndOfTurn, 2), 
                                                        new TapTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, true)));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private MasterApothecary(final MasterApothecary card) {
        super(card);
    }

    @Override
    public MasterApothecary copy() {
        return new MasterApothecary(this);
    }
}
