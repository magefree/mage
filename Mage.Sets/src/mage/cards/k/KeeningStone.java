
package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author North
 */
public final class KeeningStone extends CardImpl {

    public KeeningStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // {5}, {tap}: Target player puts the top X cards of their library into their graveyard, where X is the number of cards in that player's graveyard.
        Ability ability = new SimpleActivatedAbility(new KeeningStoneEffect(), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private KeeningStone(final KeeningStone card) {
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
        this.staticText = "Target player mills X cards, where X is the number of cards in that player's graveyard";
    }

    private KeeningStoneEffect(final KeeningStoneEffect effect) {
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
            player.millCards(player.getGraveyard().size(), source, game);
            return true;
        }
        return false;
    }
}
