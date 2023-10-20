package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.TwoChoiceVote;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TruthOrConsequences extends CardImpl {

    public TruthOrConsequences(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{R}");

        // Secret council -- Each player secretly votes for truth or consequences, then those votes are revealed. You draw cards equal to the number of truth votes. Then choose an opponent at random. Truth or Consequences deals 3 damage to that player for each consequences vote.
        this.getSpellAbility().addEffect(new TruthOrConsequencesEffect());
        this.getSpellAbility().setAbilityWord(AbilityWord.SECRET_COUNCIL);
    }

    private TruthOrConsequences(final TruthOrConsequences card) {
        super(card);
    }

    @Override
    public TruthOrConsequences copy() {
        return new TruthOrConsequences(this);
    }
}

class TruthOrConsequencesEffect extends OneShotEffect {

    TruthOrConsequencesEffect() {
        super(Outcome.Benefit);
        staticText = "each player secretly votes for truth or consequences, then those votes are revealed. " +
                "You draw cards equal to the number of truth votes. Then choose an opponent at random. " +
                "{this} deals 3 damage to that player for each consequences vote";
    }

    private TruthOrConsequencesEffect(final TruthOrConsequencesEffect effect) {
        super(effect);
    }

    @Override
    public TruthOrConsequencesEffect copy() {
        return new TruthOrConsequencesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TwoChoiceVote vote = new TwoChoiceVote("Truth (draw card)", "Consequences (deal damage)", Outcome.DrawCard, true);
        vote.doVotes(source, game);
        player.drawCards(vote.getVoteCount(true), source, game);
        TargetOpponent target = new TargetOpponent(true);
        target.setRandom(true);
        target.choose(outcome, source.getControllerId(), source.getSourceId(), source, game);
        Player opponent = game.getPlayer(target.getFirstTarget());
        return opponent == null || opponent.damage(3 * vote.getVoteCount(false), source, game) > 0;
    }
}
