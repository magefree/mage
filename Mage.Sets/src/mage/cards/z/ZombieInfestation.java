
package mage.cards.z;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ZombieToken;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author North
 */
public final class ZombieInfestation extends CardImpl {

    public ZombieInfestation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");

        // Discard two cards: Create a 2/2 black Zombie creature token.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new CreateTokenEffect(new ZombieToken()),
                new DiscardTargetCost(new TargetCardInHand(2, StaticFilters.FILTER_CARD_CARDS)));
        this.addAbility(ability);
    }

    private ZombieInfestation(final ZombieInfestation card) {
        super(card);
    }

    @Override
    public ZombieInfestation copy() {
        return new ZombieInfestation(this);
    }
}
