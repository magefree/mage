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
 * @author LevelX2
 */
public class SearchLibraryPutInPlayTargetPlayerEffect extends SearchEffect {

    protected boolean tapped;
    protected boolean forceShuffle;
    protected boolean ownerIsController;

    public SearchLibraryPutInPlayTargetPlayerEffect(TargetCardInLibrary target) {
        this(target, false, true, Outcome.PutCardInPlay);
    }

    public SearchLibraryPutInPlayTargetPlayerEffect(TargetCardInLibrary target, boolean tapped) {
        this(target, tapped, true, Outcome.PutCardInPlay);
    }

    public SearchLibraryPutInPlayTargetPlayerEffect(TargetCardInLibrary target, boolean tapped, boolean forceShuffle) {
        this(target, tapped, forceShuffle, Outcome.PutCardInPlay);
    }

    public SearchLibraryPutInPlayTargetPlayerEffect(TargetCardInLibrary target, boolean tapped, Outcome outcome) {
        this(target, tapped, true, outcome);
    }

    public SearchLibraryPutInPlayTargetPlayerEffect(TargetCardInLibrary target, boolean tapped, boolean forceShuffle, Outcome outcome) {
        this(target, tapped, forceShuffle, outcome, false);
    }

    public SearchLibraryPutInPlayTargetPlayerEffect(TargetCardInLibrary target, boolean tapped, boolean forceShuffle, Outcome outcome, boolean ownerIsController) {
        super(target, outcome);
        this.tapped = tapped;
        this.forceShuffle = forceShuffle;
        this.ownerIsController = ownerIsController;
        setText();
    }

    public SearchLibraryPutInPlayTargetPlayerEffect(final SearchLibraryPutInPlayTargetPlayerEffect effect) {
        super(effect);
        this.tapped = effect.tapped;
        this.forceShuffle = effect.forceShuffle;
        this.ownerIsController = effect.ownerIsController;
    }

    @Override
    public SearchLibraryPutInPlayTargetPlayerEffect copy() {
        return new SearchLibraryPutInPlayTargetPlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null) {
            if (player.searchLibrary(target, source, game)) {
                if (!target.getTargets().isEmpty()) {
                    player.moveCards(new CardsImpl(target.getTargets()).getCards(game),
                            Zone.BATTLEFIELD, source, game, tapped, false, ownerIsController, null);
                }
                player.shuffleLibrary(source, game);
                return true;
            }

            if (forceShuffle) {
                player.shuffleLibrary(source, game);
            }
        }

        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("target player searches their library for ");
        if (target.getNumberOfTargets() == 0 && target.getMaxNumberOfTargets() > 0) {
            if (target.getMaxNumberOfTargets() == Integer.MAX_VALUE) {
                sb.append("any number of ").append(' ');
            } else {
                sb.append("up to ").append(target.getMaxNumberOfTargets()).append(' ');
            }
            sb.append(target.getTargetName()).append(", puts them onto the battlefield");
        } else {
            sb.append("a ").append(target.getTargetName()).append(", puts it onto the battlefield");
        }
        if (tapped) {
            sb.append(" tapped");
        }
        if (forceShuffle) {
            sb.append(", then shuffles");
        } else {
            sb.append(". If that player does, they shuffle");
        }
        staticText = sb.toString();
    }

    public List<UUID> getTargets() {
        return target.getTargets();
    }

}
