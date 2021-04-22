package mage.abilities.effects.common.search;

import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

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
        setText();
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
        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("search your library for ");
        if (target.getNumberOfTargets() == 0 && target.getMaxNumberOfTargets() > 0) {
            if (target.getMaxNumberOfTargets() == Integer.MAX_VALUE) {
                sb.append("any number of ");
            } else {
                sb.append("up to ").append(CardUtil.numberToText(target.getMaxNumberOfTargets())).append(' ');
            }
            sb.append(target.getTargetName());
            sb.append(forceShuffle ? ", " : " and ");
            sb.append("put them onto the battlefield");
        } else {
            sb.append(CardUtil.addArticle(target.getTargetName()));
            sb.append(forceShuffle ? ", " : " and ");
            sb.append("put it onto the battlefield");
        }
        if (tapped) {
            sb.append(" tapped");
        }
        if (forceShuffle) {
            sb.append(", then shuffle");
        } else {
            sb.append(". If you do, shuffle");
        }
        staticText = sb.toString();
    }

    public List<UUID> getTargets() {
        return target.getTargets();
    }

}
