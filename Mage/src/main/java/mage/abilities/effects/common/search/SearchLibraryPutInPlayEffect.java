package mage.abilities.effects.common.search;

import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.List;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class SearchLibraryPutInPlayEffect extends SearchEffect {

    protected boolean tapped;
    protected boolean forceShuffle;

    public SearchLibraryPutInPlayEffect(TargetCardInLibrary target) {
        this(target, false, true, Outcome.PutCardInPlay);
    }

    public SearchLibraryPutInPlayEffect(TargetCardInLibrary target, boolean tapped) {
        this(target, tapped, true, Outcome.PutCardInPlay);
    }

    public SearchLibraryPutInPlayEffect(TargetCardInLibrary target, boolean tapped, boolean forceShuffle) {
        this(target, tapped, forceShuffle, Outcome.PutCardInPlay);
    }

    public SearchLibraryPutInPlayEffect(TargetCardInLibrary target, boolean tapped, Outcome outcome) {
        this(target, tapped, true, outcome);
    }

    public SearchLibraryPutInPlayEffect(TargetCardInLibrary target, boolean tapped, boolean forceShuffle, Outcome outcome) {
        super(target, outcome);
        this.tapped = tapped;
        this.forceShuffle = forceShuffle;
        staticText = "search your library for "
                + target.getDescription()
                + (forceShuffle ? ", " : " and ")
                + (target.getMaxNumberOfTargets() > 1 ? "put them onto the battlefield" : "put it onto the battlefield")
                + (tapped ? " tapped" : "")
                + (forceShuffle ? ", then shuffle" : ". If you do, shuffle");
    }

    public SearchLibraryPutInPlayEffect(final SearchLibraryPutInPlayEffect effect) {
        super(effect);
        this.tapped = effect.tapped;
        this.forceShuffle = effect.forceShuffle;
    }

    @Override
    public SearchLibraryPutInPlayEffect copy() {
        return new SearchLibraryPutInPlayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (player.searchLibrary(target, source, game)) {
            if (!target.getTargets().isEmpty()) {
                player.moveCards(new CardsImpl(target.getTargets()).getCards(game),
                        Zone.BATTLEFIELD, source, game, tapped, false, false, null);
            }
            player.shuffleLibrary(source, game);
            return true;
        }
        if (forceShuffle) {
            player.shuffleLibrary(source, game);
        }
        return true;
    }

    public List<UUID> getTargets() {
        return target.getTargets();
    }
}
