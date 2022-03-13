
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileCardYouChooseTargetOpponentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class Doomfall extends CardImpl {

    public Doomfall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Choose one —
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);

        // • Target opponent exiles a creature they control.
        this.getSpellAbility().addEffect(new DoomfallEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

        // • Target opponent reveals their hand. You choose a nonland card from it. Exile that card.
        Mode mode = new Mode(new ExileCardYouChooseTargetOpponentEffect(StaticFilters.FILTER_CARD_A_NON_LAND)
                .setText("Target opponent reveals their hand. You choose a nonland card from it. Exile that card"));
        mode.addTarget(new TargetOpponent());
        this.getSpellAbility().addMode(mode);
    }

    private Doomfall(final Doomfall card) {
        super(card);
    }

    @Override
    public Doomfall copy() {
        return new Doomfall(this);
    }
}

class DoomfallEffect extends OneShotEffect {

    public DoomfallEffect() {
        super(Outcome.Exile);
        this.staticText = "target player exiles a creature they control";
    }

    public DoomfallEffect(final DoomfallEffect effect) {
        super(effect);
    }

    @Override
    public DoomfallEffect copy() {
        return new DoomfallEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            Target target = new TargetControlledCreaturePermanent();
            target.setNotTarget(true);
            if (targetPlayer.choose(outcome, target, source, game)) {
                targetPlayer.moveCards(game.getPermanent(target.getFirstTarget()), Zone.EXILED, source, game);
            }
            return true;
        }
        return false;
    }
}
