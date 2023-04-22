
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author L_J
 */
public final class AshnodsCylix extends CardImpl {

    public AshnodsCylix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {3}, {T}: Target player looks at the top three cards of their library, puts one of them back on top of their library, then exiles the rest.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AshnodsCylixEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private AshnodsCylix(final AshnodsCylix card) {
        super(card);
    }

    @Override
    public AshnodsCylix copy() {
        return new AshnodsCylix(this);
    }
}

class AshnodsCylixEffect extends OneShotEffect {

    AshnodsCylixEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target player looks at the top three cards of their library, puts one of them back on top of their library, then exiles the rest";
    }

    AshnodsCylixEffect(final AshnodsCylixEffect effect) {
        super(effect);
    }

    @Override
    public AshnodsCylixEffect copy() {
        return new AshnodsCylixEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 3));
        player.lookAtCards(source, null, cards, game);
        TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to put on top of your library"));
        if (player.choose(Outcome.Benefit, cards, target, source, game)) {
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                game.informPlayers(source.getSourceObject(game).getIdName() + ": " + player.getLogName() + " puts a card back on top of their library");
            }
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        return true;
    }
}
