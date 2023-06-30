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
    protected boolean textThatCard;
    protected boolean optional;

    public SearchLibraryPutInPlayEffect(TargetCardInLibrary target) {
        this(target, false);
    }

    public SearchLibraryPutInPlayEffect(TargetCardInLibrary target, boolean tapped) {
        this(target, tapped, false);
    }

    public SearchLibraryPutInPlayEffect(TargetCardInLibrary target, boolean tapped, boolean textThatCard) {
        this(target, tapped, textThatCard, false);
    }

    public SearchLibraryPutInPlayEffect(TargetCardInLibrary target, boolean tapped, boolean textThatCard, boolean optional) {
        super(target, Outcome.PutCardInPlay);
        this.tapped = tapped;
        this.textThatCard = textThatCard;
        this.optional = optional;
        if (target.getDescription().contains("land")) {
            this.outcome = Outcome.PutLandInPlay;
        } else if (target.getDescription().contains("creature")) {
            this.outcome = Outcome.PutCreatureInPlay;
        }
        setText();
    }

    public SearchLibraryPutInPlayEffect(final SearchLibraryPutInPlayEffect effect) {
        super(effect);
        this.tapped = effect.tapped;
        this.textThatCard = effect.textThatCard;
        this.optional = effect.optional;
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
        if (optional && !player.chooseUse(outcome, "Search your library for " + target.getDescription() + '?', source, game)) {
            return true;
        }
        if (player.searchLibrary(target, source, game)) {
            if (!target.getTargets().isEmpty()) {
                player.moveCards(new CardsImpl(target.getTargets()).getCards(game),
                        Zone.BATTLEFIELD, source, game, tapped, false, false, null);
            }
            player.shuffleLibrary(source, game);
            return true;
        }
        player.shuffleLibrary(source, game);
        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        if (optional) {
            sb.append("you may ");
        }
        sb.append("search your library for ");
        sb.append(target.getDescription());
        sb.append(", put ");
        if (target.getMaxNumberOfTargets() > 1) {
            sb.append(textThatCard ? "those cards" : "them");
        } else {
            sb.append(textThatCard ? "that card" : "it");
        }
        sb.append(" onto the battlefield");
        if (tapped) {
            sb.append(" tapped");
        }
        sb.append( ", then shuffle");
        staticText = sb.toString();
    }

    public List<UUID> getTargets() {
        return target.getTargets();
    }
}
