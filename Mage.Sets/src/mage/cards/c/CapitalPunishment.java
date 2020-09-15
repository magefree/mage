package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CouncilsDilemmaVoteEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author JRHerlehy
 */
public final class CapitalPunishment extends CardImpl {

    public CapitalPunishment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // <i>Council's dilemma</i> &mdash; Starting with you, each player votes for death or taxes. Each opponent sacrifices a creature for each death vote and discards a card for each taxes vote.
        this.getSpellAbility().addEffect(new CapitalPunishmentDilemmaEffect());
    }

    public CapitalPunishment(final CapitalPunishment card) {
        super(card);
    }

    @Override
    public CapitalPunishment copy() {
        return new CapitalPunishment(this);
    }
}

class CapitalPunishmentDilemmaEffect extends CouncilsDilemmaVoteEffect {

    public CapitalPunishmentDilemmaEffect() {
        super(Outcome.Detriment);
        this.staticText = "<i>Council's dilemma</i> &mdash; Starting with you, each player votes for death or taxes. Each opponent sacrifices a creature for each death vote and discards a card for each taxes vote";
    }

    public CapitalPunishmentDilemmaEffect(final CapitalPunishmentDilemmaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        //If no controller, exit out here and do not vote.
        if (controller == null) {
            return false;
        }

        this.vote("death", "taxes", controller, game, source);

        //Death Votes
        if (voteOneCount > 0) {
            Effect sacrificeEffect = new SacrificeOpponentsEffect(voteOneCount, StaticFilters.FILTER_CONTROLLED_CREATURE);
            sacrificeEffect.apply(game, source);
        }

        //Taxes Votes
        if (voteTwoCount > 0) {
            Effect discardEffect = new DiscardEachPlayerEffect(StaticValue.get(voteTwoCount), false, TargetController.OPPONENT);
            discardEffect.apply(game, source);
        }

        return true;
    }

    @Override
    public CapitalPunishmentDilemmaEffect copy() {
        return new CapitalPunishmentDilemmaEffect(this);
    }
}
