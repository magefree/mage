package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.TwoChoiceVote;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.UUID;

/**
 * @author JRHerlehy, TheElk801
 */
public final class CapitalPunishment extends CardImpl {

    public CapitalPunishment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // <i>Council's dilemma</i> &mdash; Starting with you, each player votes for death or taxes. Each opponent sacrifices a creature for each death vote and discards a card for each taxes vote.
        this.getSpellAbility().addEffect(new CapitalPunishmentEffect());
    }

    private CapitalPunishment(final CapitalPunishment card) {
        super(card);
    }

    @Override
    public CapitalPunishment copy() {
        return new CapitalPunishment(this);
    }
}

class CapitalPunishmentEffect extends OneShotEffect {

    CapitalPunishmentEffect() {
        super(Outcome.Benefit);
        this.staticText = "<i>Council's dilemma</i> &mdash; Starting with you, each player votes for death or taxes. " +
                "Each opponent sacrifices a creature for each death vote and discards a card for each taxes vote";
    }

    private CapitalPunishmentEffect(final CapitalPunishmentEffect effect) {
        super(effect);
    }

    @Override
    public CapitalPunishmentEffect copy() {
        return new CapitalPunishmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Outcome.Detriment - AI will discard a card all the time (taxes choice)
        // TODO: add AI hint logic in the choice method, see Tyrant's Choice as example
        TwoChoiceVote vote = new TwoChoiceVote("Death (sacrifice creature)", "Taxes (discard card)", Outcome.Detriment);
        vote.doVotes(source, game);

        int deathCount = vote.getVoteCount(true);
        int taxesCount = vote.getVoteCount(false);
        if (deathCount > 0) {
            new SacrificeOpponentsEffect(
                    deathCount, StaticFilters.FILTER_CONTROLLED_CREATURE
            ).apply(game, source);
        }
        if (taxesCount > 0) {
            new DiscardEachPlayerEffect(
                    StaticValue.get(taxesCount), false, TargetController.OPPONENT
            ).apply(game, source);
        }
        return true;
    }
}
