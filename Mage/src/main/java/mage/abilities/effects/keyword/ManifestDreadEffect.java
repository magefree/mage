package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

/**
 * @author TheElk801
 */
public class ManifestDreadEffect extends OneShotEffect {

    public ManifestDreadEffect() {
        super(Outcome.Benefit);
        staticText = "manifest dread";
    }

    private ManifestDreadEffect(final ManifestDreadEffect effect) {
        super(effect);
    }

    @Override
    public ManifestDreadEffect copy() {
        return new ManifestDreadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && doManifestDread(player, source, game);
    }

    public static boolean doManifestDread(Player player, Ability source, Game game) {
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 2));
        Card card;
        switch (cards.size()) {
            case 0:
                return false;
            case 1:
                card = cards.getRandom(game);
                break;
            default:
                TargetCard target = new TargetCardInLibrary();
                target.withChooseHint("to manifest");
                player.choose(Outcome.PutCreatureInPlay, target, source, game);
                card = cards.get(target.getFirstTarget(), game);
        }
        if (card != null) {
            ManifestEffect.doManifestCards(game, source, player, new CardsImpl(card).getCards(game));
        }
        cards.retainZone(Zone.LIBRARY, game);
        player.moveCards(cards, Zone.GRAVEYARD, source, game);
        return true;
    }
}
