
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author jonubuu
 */
public final class SeismicAssault extends CardImpl {

    private static final FilterCard filter = new FilterLandCard();

    public SeismicAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{R}{R}{R}");


        // Discard a land card: Seismic Assault deals 2 damage to any target.       
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new DiscardTargetCost(new TargetCardInHand(filter)));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    public SeismicAssault(final SeismicAssault card) {
        super(card);
    }

    @Override
    public SeismicAssault copy() {
        return new SeismicAssault(this);
    }
}
