
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author fireshoes
 */
public final class ConchHorn extends CardImpl {

    public ConchHorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {1}, {tap}, Sacrifice Conch Horn: Draw two cards, then put a card from your hand on top of your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ConchHornEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private ConchHorn(final ConchHorn card) {
        super(card);
    }

    @Override
    public ConchHorn copy() {
        return new ConchHorn(this);
    }
}

class ConchHornEffect extends OneShotEffect {

    public ConchHornEffect() {
        super(Outcome.DrawCard);
        staticText = "Draw two cards, then put a card from your hand on top of your library";
    }

    public ConchHornEffect(final ConchHornEffect effect) {
        super(effect);
    }

    @Override
    public ConchHornEffect copy() {
        return new ConchHornEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(2, source, game);
            putOnLibrary(player, source, game);
            return true;
        }
        return false;
    }

    private boolean putOnLibrary(Player player, Ability source, Game game) {
        TargetCardInHand target = new TargetCardInHand();
        if (target.canChoose(player.getId(), source, game)) {
            player.chooseTarget(Outcome.ReturnToHand, target, source, game);
            Card card = player.getHand().get(target.getFirstTarget(), game);
            if (card != null) {
                return player.moveCardToLibraryWithInfo(card, source, game, Zone.HAND, true, false);
            }
        }
        return false;
    }
}
