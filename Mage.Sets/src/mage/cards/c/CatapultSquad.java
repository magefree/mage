
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class CatapultSquad extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Soldiers you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.SOLDIER.getPredicate());
    }

    public CatapultSquad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Tap two untapped Soldiers you control: Catapult Squad deals 2 damage to target attacking or blocking creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new TapTargetCost(new TargetControlledPermanent(2, 2, filter, false)));
        ability.addTarget(new TargetCreaturePermanent(new FilterAttackingOrBlockingCreature()));
        this.addAbility(ability);
    }

    private CatapultSquad(final CatapultSquad card) {
        super(card);
    }

    @Override
    public CatapultSquad copy() {
        return new CatapultSquad(this);
    }
}
