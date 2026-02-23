package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.assignment.common.CardTypeAssignment;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class PutCreatureAndOrLandFromHandOntoBattlefieldEffect extends OneShotEffect {

    public PutCreatureAndOrLandFromHandOntoBattlefieldEffect() {
        super(Outcome.Benefit);
        staticText = "you may put a creature card and/or a land card from your hand onto the battlefield";
    }

    private PutCreatureAndOrLandFromHandOntoBattlefieldEffect(final PutCreatureAndOrLandFromHandOntoBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public PutCreatureAndOrLandFromHandOntoBattlefieldEffect copy() {
        return new PutCreatureAndOrLandFromHandOntoBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new CreatureAndOrLandInHandTarget();
        player.choose(outcome, player.getHand(), target, source, game);
        return player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
    }
}

class CreatureAndOrLandInHandTarget extends TargetCardInHand {

    private static final CardTypeAssignment cardTypeAssigner = new CardTypeAssignment(CardType.CREATURE, CardType.LAND);

    CreatureAndOrLandInHandTarget() {
        super(0, 2, StaticFilters.FILTER_CARD_CREATURE_OR_LAND);
    }

    private CreatureAndOrLandInHandTarget(final CreatureAndOrLandInHandTarget target) {
        super(target);
    }

    @Override
    public CreatureAndOrLandInHandTarget copy() {
        return new CreatureAndOrLandInHandTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability ability, Game game) {
        if (!super.canTarget(playerId, id, ability, game)) {
            return false;
        }
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        if (this.getTargets().isEmpty()) {
            return true;
        }
        Cards cards = new CardsImpl(this.getTargets());
        cards.add(card);
        return cardTypeAssigner.getRoleCount(cards, game) >= cards.size();
    }


    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);
        possibleTargets.removeIf(uuid -> !this.canTarget(sourceControllerId, uuid, source, game));
        return possibleTargets;
    }
}
