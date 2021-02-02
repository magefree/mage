
package mage.cards.t;

import java.util.UUID;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author markedagain
 */
public final class TidalBore extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an Island");

    static {
        filter.add(SubType.ISLAND.getPredicate());
        filter.add(CardType.LAND.getPredicate());
    }
    
    public TidalBore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // You may return an Island you control to its owner's hand rather than pay Tidal Bore's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(filter))));
        // You may tap or untap target creature.
        this.getSpellAbility().addEffect(new MayTapOrUntapTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private TidalBore(final TidalBore card) {
        super(card);
    }

    @Override
    public TidalBore copy() {
        return new TidalBore(this);
    }
}
