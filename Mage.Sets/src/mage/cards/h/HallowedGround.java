
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Styxo
 */
public final class HallowedGround extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent("nonsnow land you control");

    static {
        filter.add(Predicates.not(SuperType.SNOW.getPredicate()));
    }

    public HallowedGround(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // {W}{W}: Return target nonsnow land you control to its owner's hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), new ManaCostsImpl<>("{W}{W}"));
        ability.addTarget(new TargetControlledPermanent(filter));
        this.addAbility(ability);
    }

    private HallowedGround(final HallowedGround card) {
        super(card);
    }

    @Override
    public HallowedGround copy() {
        return new HallowedGround(this);
    }
}
