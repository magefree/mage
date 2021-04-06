package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.TwoChoiceVote;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.UUID;

/**
 * @author fireshoes, TheElk801
 */
public final class TyrantsChoice extends CardImpl {

    public TyrantsChoice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Will of the council - Starting with you, each player votes for death or torture. If death gets more votes, each opponent sacrifices a creature. If torture gets more votes or the vote is tied, each opponent loses 4 life.
        this.getSpellAbility().addEffect(new TyrantsChoiceEffect());
    }

    private TyrantsChoice(final TyrantsChoice card) {
        super(card);
    }

    @Override
    public TyrantsChoice copy() {
        return new TyrantsChoice(this);
    }
}

class TyrantsChoiceEffect extends OneShotEffect {

    TyrantsChoiceEffect() {
        super(Outcome.Benefit);
        this.staticText = "<i>Will of the council</i> &mdash; Starting with you, " +
                "each player votes for death or torture. If death gets more votes, " +
                "each opponent sacrifices a creature. If torture gets more votes " +
                "or the vote is tied, each opponent loses 4 life";
    }

    private TyrantsChoiceEffect(final TyrantsChoiceEffect effect) {
        super(effect);
    }

    @Override
    public TyrantsChoiceEffect copy() {
        return new TyrantsChoiceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TwoChoiceVote vote = new TwoChoiceVote("Death (sacrifice a creature)", "Torture (lose 4 life)", Outcome.Benefit);
        vote.doVotes(source, game, (voteHandler, aiPlayer, aiDecidingPlayer, aiSource, aiGame) -> {
            // ai hint
            if (aiSource.isControlledBy(aiDecidingPlayer.getId())) {
                // best for controller - lose life
                return Boolean.FALSE;
            } else {
                // best for opponent - sacrifice
                return Boolean.TRUE;
            }
        });

        int deathCount = vote.getVoteCount(true);
        int tortureCount = vote.getVoteCount(false);
        if (deathCount > tortureCount) {
            return new SacrificeOpponentsEffect(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT).apply(game, source);
        } else {
            return new LoseLifeOpponentsEffect(4).apply(game, source);
        }
    }
}
