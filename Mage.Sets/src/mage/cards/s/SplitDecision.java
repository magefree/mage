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
import mage.game.stack.Spell;
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
        super(Outcome.Removal); // cause AI votes for counter all the time so it must it target opponent's spell, not own
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
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (spell == null) {
            return false;
        }

        // Outcome.Benefit - AI will use counter all the time (Denial choice)
        // TODO: add AI hint logic in the choice method, see Tyrant's Choice as example
        TwoChoiceVote vote = new TwoChoiceVote(
                "Denial (counter " + spell.getIdName() + ")",
                "Duplication (copy " + spell.getIdName() + ")",
                Outcome.Benefit
        );
        vote.doVotes(source, game);

        int denialCount = vote.getVoteCount(true);
        int duplicationCount = vote.getVoteCount(false);
        if (denialCount > duplicationCount) {
            return game.getStack().counter(spell.getId(), source, game);
        } else {
            return new CopyTargetSpellEffect().apply(game, source);
        }
    }
}
