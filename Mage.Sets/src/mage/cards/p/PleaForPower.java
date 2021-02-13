package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.TwoChoiceVote;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author emerald000, TheElk801
 */
public final class PleaForPower extends CardImpl {

    public PleaForPower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Will of the council - Starting with you, each player votes for time or knowledge. If time gets more votes, take an extra turn after this one. If knowledge gets more votes or the vote is tied, draw three cards.
        this.getSpellAbility().addEffect(new PleaForPowerEffect());
    }

    private PleaForPower(final PleaForPower card) {
        super(card);
    }

    @Override
    public PleaForPower copy() {
        return new PleaForPower(this);
    }
}

class PleaForPowerEffect extends OneShotEffect {

    PleaForPowerEffect() {
        super(Outcome.Benefit);
        this.staticText = "<i>Will of the council</i> &mdash; Starting with you, " +
                "each player votes for time or knowledge. If time gets more votes, take an extra turn after this one. " +
                "If knowledge gets more votes or the vote is tied, draw three cards";
    }

    private PleaForPowerEffect(final PleaForPowerEffect effect) {
        super(effect);
    }

    @Override
    public PleaForPowerEffect copy() {
        return new PleaForPowerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TwoChoiceVote vote = new TwoChoiceVote("time", "knowledge");
        vote.doVotes(source, game);
        if (vote.getVoteCount(true) > vote.getVoteCount(false)) {
            return new AddExtraTurnControllerEffect().apply(game, source);
        }
        Player player = game.getPlayer(source.getControllerId());
        player.drawCards(3, source, game);
        return true;
    }
}
