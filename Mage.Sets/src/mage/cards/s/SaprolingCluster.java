
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author fireshoes
 */
public final class SaprolingCluster extends CardImpl {

    public SaprolingCluster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}");

        // {1}, Discard a card: Create a 1/1 green Saproling creature token. Any player may activate this ability.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SaprolingToken()) , new GenericManaCost(1));
        ability.addCost(new DiscardCardCost());
        ability.setMayActivate(TargetController.ANY);
        ability.addEffect(new InfoEffect("Any player may activate this ability"));
        this.addAbility(ability);
    }

    private SaprolingCluster(final SaprolingCluster card) {
        super(card);
    }

    @Override
    public SaprolingCluster copy() {
        return new SaprolingCluster(this);
    }
}
