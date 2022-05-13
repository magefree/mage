
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterEnchantmentCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class CrystalChimes extends CardImpl {

    public CrystalChimes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {3}, {tap}, Sacrifice Crystal Chimes: Return all enchantment cards from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CrystalChimesEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private CrystalChimes(final CrystalChimes card) {
        super(card);
    }

    @Override
    public CrystalChimes copy() {
        return new CrystalChimes(this);
    }
}

class CrystalChimesEffect extends OneShotEffect {

    CrystalChimesEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Return all enchantment cards from your graveyard to your hand";
    }

    CrystalChimesEffect(final CrystalChimesEffect effect) {
        super(effect);
    }

    @Override
    public CrystalChimesEffect copy() {
        return new CrystalChimesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return controller.moveCards(controller.getGraveyard().getCards(new FilterEnchantmentCard(),
                    source.getControllerId(), source, game), Zone.HAND, source, game);
        }
        return false;
    }
}
