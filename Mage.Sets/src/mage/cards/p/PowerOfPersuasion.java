package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PowerOfPersuasion extends CardImpl {

    public PowerOfPersuasion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Choose target creature an opponent controls, then roll a d20.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect(
                20, "choose target creature an opponent controls, then roll a d20"
        );
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());

        // 1-9 | Return it to its owner's hand.
        effect.addTableEntry(1, 9, new ReturnToHandTargetEffect().setText("return it to its owner's hand"));

        // 10-19 | Its owner puts it on the top of bottom of their library.
        effect.addTableEntry(10, 19, new PowerOfPersuasionEffect());

        // 20 | Gain control of it until the end of your next turn.
        effect.addTableEntry(20, 20, new GainControlTargetEffect(
                Duration.UntilEndOfYourNextTurn, true
        ).setText("gain control of it until the end of your next turn"));
    }

    private PowerOfPersuasion(final PowerOfPersuasion card) {
        super(card);
    }

    @Override
    public PowerOfPersuasion copy() {
        return new PowerOfPersuasion(this);
    }
}

class PowerOfPersuasionEffect extends OneShotEffect {

    PowerOfPersuasionEffect() {
        super(Outcome.Benefit);
        staticText = "its owner puts it on the top or bottom of their library";
    }

    private PowerOfPersuasionEffect(final PowerOfPersuasionEffect effect) {
        super(effect);
    }

    @Override
    public PowerOfPersuasionEffect copy() {
        return new PowerOfPersuasionEffect(this);
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
