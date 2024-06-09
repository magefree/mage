

package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author Loki
 */
public final class BenBenAkkiHermit extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("untapped Mountain you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public BenBenAkkiHermit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN, SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(new PermanentsOnBattlefieldCount(filter), true), new TapSourceCost());
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private BenBenAkkiHermit(final BenBenAkkiHermit card) {
        super(card);
    }

    @Override
    public BenBenAkkiHermit copy() {
        return new BenBenAkkiHermit(this);
    }

}
