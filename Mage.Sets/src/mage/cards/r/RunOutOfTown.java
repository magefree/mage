package mage.cards.r;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author weirddan455
 */
public final class RunOutOfTown extends CardImpl {

    public RunOutOfTown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // The owner of target nonland permanent puts it on the top or bottom of their library.
        this.getSpellAbility().addEffect(new RunOutOfTownEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private RunOutOfTown(final RunOutOfTown card) {
        super(card);
    }

    @Override
    public RunOutOfTown copy() {
        return new RunOutOfTown(this);
    }
}

class RunOutOfTownEffect extends OneShotEffect {

    public RunOutOfTownEffect() {
        super(Outcome.Removal);
        this.staticText = "The owner of target nonland permanent puts it on the top or bottom of their library";
    }

    private RunOutOfTownEffect(final RunOutOfTownEffect effect) {
        super(effect);
    }

    @Override
    public RunOutOfTownEffect copy() {
        return new RunOutOfTownEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getOwnerId(source.getFirstTarget()));
        if (player == null) {
            return false;
        }
        if (player.chooseUse(Outcome.Detriment, "Put the targeted object on the top or bottom of your library?",
                "", "Top", "Bottom", source, game)) {
            return new PutOnLibraryTargetEffect(true).apply(game, source);
        }
        return new PutOnLibraryTargetEffect(false).apply(game, source);
    }
}
