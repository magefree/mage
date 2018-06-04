
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public final class KeeningStone extends CardImpl {

    public KeeningStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{6}");

        // {5}, {tap}: Target player puts the top X cards of their library into their graveyard, where X is the number of cards in that player's graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new KeeningStoneEffect(), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public KeeningStone(final KeeningStone card) {
        super(card);
    }

    @Override
    public KeeningStone copy() {
        return new KeeningStone(this);
    }
}

class KeeningStoneEffect extends OneShotEffect {

    public KeeningStoneEffect() {
        super(Outcome.Neutral);
        this.staticText = "Target player puts the top X cards of their library into their graveyard, where X is the number of cards in that player's graveyard";
    }

    public KeeningStoneEffect(final KeeningStoneEffect effect) {
        super(effect);
    }

    @Override
    public KeeningStoneEffect copy() {
        return new KeeningStoneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            player.moveCards(player.getLibrary().getTopCards(game, player.getGraveyard().size()), Zone.GRAVEYARD, source, game);
            return true;
        }
        return false;
    }
}
