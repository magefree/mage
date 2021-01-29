package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RunAshore extends CardImpl {

    public RunAshore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");

        // Choose one or both —
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // • The owner of target nonland permanent puts it on the top or bottom of their library.
        this.getSpellAbility().addEffect(new RunAshoreEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());

        // • Return target nonland permanent to its owner's hand.
        Mode mode = new Mode(new ReturnToHandTargetEffect());
        mode.addTarget(new TargetNonlandPermanent());
        this.getSpellAbility().addMode(mode);
    }

    private RunAshore(final RunAshore card) {
        super(card);
    }

    @Override
    public RunAshore copy() {
        return new RunAshore(this);
    }
}

class RunAshoreEffect extends OneShotEffect {

    RunAshoreEffect() {
        super(Outcome.Benefit);
        staticText = "The owner of target nonland permanent puts it on the top or bottom of their library.";
    }

    private RunAshoreEffect(final RunAshoreEffect effect) {
        super(effect);
    }

    @Override
    public RunAshoreEffect copy() {
        return new RunAshoreEffect(this);
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
