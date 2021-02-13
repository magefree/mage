package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.TwoChoiceVote;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author fireshoes, TheElk801
 */
public final class SplitDecision extends CardImpl {

    public SplitDecision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Will of the council - Choose target instant or sorcery spell. Starting with you, each player votes for denial or duplication. If denial gets more votes, counter the spell. If duplication gets more votes or the vote is tied, copy the spell. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(new SplitDecisionEffect());
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));
    }

    private SplitDecision(final SplitDecision card) {
        super(card);
    }

    @Override
    public SplitDecision copy() {
        return new SplitDecision(this);
    }
}

class SplitDecisionEffect extends OneShotEffect {

    SplitDecisionEffect() {
        super(Outcome.Benefit);
        this.staticText = "<i>Will of the council</i> &mdash; Choose target instant or sorcery spell. " +
                "Starting with you, each player votes for denial or duplication. If denial gets more votes, " +
                "counter the spell. If duplication gets more votes or the vote is tied, copy the spell. " +
                "You may choose new targets for the copy";
    }

    private SplitDecisionEffect(final SplitDecisionEffect effect) {
        super(effect);
    }

    @Override
    public SplitDecisionEffect copy() {
        return new SplitDecisionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TwoChoiceVote vote = new TwoChoiceVote("denial", "duplication");
        vote.doVotes(source, game);

        if (vote.getVoteCount(true) > vote.getVoteCount(false)) {
            return game.getStack().counter(getTargetPointer().getFirst(game, source), source, game);
        }
        return new CopyTargetSpellEffect().apply(game, source);
    }
}
