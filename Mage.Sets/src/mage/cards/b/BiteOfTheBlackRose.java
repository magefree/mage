package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostOpponentsEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.TwoChoiceVote;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class BiteOfTheBlackRose extends CardImpl {

    public BiteOfTheBlackRose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Will of the council - Starting with you, each player votes for sickness or psychosis. If sickness gets more votes, creatures your opponents control get -2/-2 until end of turn. If psychosis gets more votes or the vote is tied, each opponent discards two cards.
        this.getSpellAbility().addEffect(new BiteOfTheBlackRoseEffect());
    }

    private BiteOfTheBlackRose(final BiteOfTheBlackRose card) {
        super(card);
    }

    @Override
    public BiteOfTheBlackRose copy() {
        return new BiteOfTheBlackRose(this);
    }
}

class BiteOfTheBlackRoseEffect extends OneShotEffect {

    BiteOfTheBlackRoseEffect() {
        super(Outcome.Benefit);
        this.staticText = "<i>Will of the council</i> &mdash; Starting with you, " +
                "each player votes for sickness or psychosis. If sickness gets more votes, " +
                "creatures your opponents control get -2/-2 until end of turn. " +
                "If psychosis gets more votes or the vote is tied, each opponent discards two cards";
    }

    private BiteOfTheBlackRoseEffect(final BiteOfTheBlackRoseEffect effect) {
        super(effect);
    }

    @Override
    public BiteOfTheBlackRoseEffect copy() {
        return new BiteOfTheBlackRoseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TwoChoiceVote vote = new TwoChoiceVote("Sickness", "Psychosis", Outcome.UnboostCreature);
        vote.doVotes(source, game);

        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (vote.getVoteCount(true) > vote.getVoteCount(false)) {
            game.addEffect(new BoostOpponentsEffect(-2, -2, Duration.EndOfTurn), source);
        } else {
            new DiscardEachPlayerEffect(
                    StaticValue.get(2), false, TargetController.OPPONENT
            ).apply(game, source);
        }
        return true;
    }
}
