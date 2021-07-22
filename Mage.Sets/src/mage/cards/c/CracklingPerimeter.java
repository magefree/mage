
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class CracklingPerimeter extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Gate you control");

    static {
        filter.add(SubType.GATE.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public CracklingPerimeter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");


        // Tap an untapped Gate you control: Crackling Perimeter deals 1 damage to each opponent.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamagePlayersEffect(1, TargetController.OPPONENT), new TapTargetCost(new TargetControlledPermanent(filter))));
    }

    private CracklingPerimeter(final CracklingPerimeter card) {
        super(card);
    }

    @Override
    public CracklingPerimeter copy() {
        return new CracklingPerimeter(this);
    }
}
