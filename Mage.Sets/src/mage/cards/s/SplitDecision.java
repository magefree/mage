
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public final class SplitDecision extends CardImpl {

    public SplitDecision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Will of the council - Choose target instant or sorcery spell. Starting with you, each player votes for denial or duplication. If denial gets more votes, counter the spell. If duplication gets more votes or the vote is tied, copy the spell. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(new SplitDecisionEffect());
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));
    }

    public SplitDecision(final SplitDecision card) {
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
        this.staticText = "<i>Will of the council</i> &mdash; Choose target instant or sorcery spell. Starting with you, each player votes for denial or duplication. If denial gets more votes, counter the spell. If duplication gets more votes or the vote is tied, copy the spell. You may choose new targets for the copy";
    }

    SplitDecisionEffect(final SplitDecisionEffect effect) {
        super(effect);
    }

    @Override
    public SplitDecisionEffect copy() {
        return new SplitDecisionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int denialCount = 0;
            int duplicationCount = 0;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.chooseUse(Outcome.ExtraTurn, "Choose denial?", source, game)) {
                        denialCount++;
                        game.informPlayers(player.getLogName() + " has voted for denial");
                    } else {
                        duplicationCount++;
                        game.informPlayers(player.getLogName() + " has voted for duplication");
                    }
                }
            }
            if (denialCount > duplicationCount) {
                return game.getStack().counter(getTargetPointer().getFirst(game, source), source.getSourceId(), game);
            } else {
                return new CopyTargetSpellEffect().apply(game, source);
            }
        }
        return false;
    }
}
