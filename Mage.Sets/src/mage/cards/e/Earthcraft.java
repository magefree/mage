
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author Loki
 */
public final class Earthcraft extends CardImpl {

    private static final FilterControlledCreaturePermanent filterCreature = new FilterControlledCreaturePermanent("untapped creature you control");
    private static final FilterControlledPermanent filterLand = new FilterControlledPermanent("basic land");

    static {
        filterCreature.add(TappedPredicate.UNTAPPED);
        filterLand.add(CardType.LAND.getPredicate());
        filterLand.add(SuperType.BASIC.getPredicate());
    }

    public Earthcraft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // Tap an untapped creature you control: Untap target basic land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapTargetEffect(), new TapTargetCost(new TargetControlledCreaturePermanent(1, 1, filterCreature, true)));
        ability.addTarget(new TargetPermanent(filterLand));
        this.addAbility(ability);
    }

    private Earthcraft(final Earthcraft card) {
        super(card);
    }

    @Override
    public Earthcraft copy() {
        return new Earthcraft(this);
    }
}
