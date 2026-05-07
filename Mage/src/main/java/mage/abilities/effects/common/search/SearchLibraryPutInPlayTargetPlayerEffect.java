package mage.abilities.effects.common.search;

import mage.abilities.Ability;
import mage.abilities.Mode;
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
    protected boolean ownerIsController;
    protected boolean optional;

    public SearchLibraryPutInPlayTargetPlayerEffect(TargetCardInLibrary target, boolean tapped) {
        this(target, tapped, false);
    }

    public SearchLibraryPutInPlayTargetPlayerEffect(TargetCardInLibrary target, boolean tapped, boolean ownerIsController) {
        this(target, tapped, ownerIsController, false);
    }

    public SearchLibraryPutInPlayTargetPlayerEffect(TargetCardInLibrary target, boolean tapped, boolean ownerIsController, boolean optional) {
        super(target, Outcome.PutCardInPlay);
        this.tapped = tapped;
        this.ownerIsController = ownerIsController;
        this.optional = optional;
        if (target.getDescription().contains("land")) {
            this.outcome = Outcome.PutLandInPlay;
        } else if (target.getDescription().contains("creature")) {
            this.outcome = Outcome.PutCreatureInPlay;
        }
    }

    protected SearchLibraryPutInPlayTargetPlayerEffect(final SearchLibraryPutInPlayTargetPlayerEffect effect) {
        super(effect);
        this.tapped = effect.tapped;
        this.ownerIsController = effect.ownerIsController;
        this.optional = effect.optional;
    }

    @Override
    public SearchLibraryPutInPlayTargetPlayerEffect copy() {
        return new SearchLibraryPutInPlayTargetPlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> targets = getTargetPointer().getTargets(game, source);
        if (targets.isEmpty()) {
            return false;
        }
        for (UUID targetId : targets) {
            Player player = game.getPlayer(targetId);
            if (player == null) {
                continue;
            }

            if (this.optional) {
                if (!player.chooseUse(outcome, "Search your library for " + target.getDescription() + '?', source, game)) {
                    continue;
                }
            }

            TargetCardInLibrary targetCopy = target.copy();
            if (player.searchLibrary(targetCopy, source, game) && !targetCopy.getTargets().isEmpty()) {
                player.moveCards(new CardsImpl(targetCopy.getTargets()).getCards(game),
                        Zone.BATTLEFIELD, source, game, tapped, false, ownerIsController, null);
            }
            player.shuffleLibrary(source, game);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return getTargetPointer().describeTargets(mode.getTargets(), "that player")
                + (optional ? " may search" : " searches")
                + " their library for "
                + target.getDescription()
                + ", "
                + (target.getMaxNumberOfTargets() > 1 ? "puts them onto the battlefield" : "puts it onto the battlefield")
                + (tapped ? " tapped" : "")
                + ", then shuffles";
    }
}
