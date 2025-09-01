package mage.cards.y;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.assignment.common.CardTypeAssignment;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YunasDecision extends CardImpl {

    public YunasDecision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Choose one --
        // * Continue the Pilgrimage -- Sacrifice a creature. If you do, draw a card, then you may put a creature card and/or a land card from your hand onto the battlefield.
        this.getSpellAbility().addEffect(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1),
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE), null, false
        ).addEffect(new YunasDecisionEffect()));
        this.getSpellAbility().withFirstModeFlavorWord("Continue the Pilgrimage");

        // * Find Another Way -- Return one or two target permanent cards from your graveyard to your hand.
        this.getSpellAbility().addMode(
                new Mode(new ReturnFromGraveyardToHandTargetEffect())
                        .addTarget(new TargetCardInYourGraveyard(
                                1, 2, StaticFilters.FILTER_CARD_PERMANENTS
                        ))
                        .withFlavorWord("Find Another Way")
        );
    }

    private YunasDecision(final YunasDecision card) {
        super(card);
    }

    @Override
    public YunasDecision copy() {
        return new YunasDecision(this);
    }
}

class YunasDecisionEffect extends OneShotEffect {

    YunasDecisionEffect() {
        super(Outcome.Benefit);
        staticText = ", then you may put a creature card and/or a land card from your hand onto the battlefield";
    }

    private YunasDecisionEffect(final YunasDecisionEffect effect) {
        super(effect);
    }

    @Override
    public YunasDecisionEffect copy() {
        return new YunasDecisionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new YunasDecisionTarget();
        player.choose(outcome, player.getHand(), target, source, game);
        return player.moveCards(
                new CardsImpl(target.getTargets()).getCards(game), Zone.BATTLEFIELD, source,
                game, true, false, false, null
        );
    }
}

class YunasDecisionTarget extends TargetCardInHand {

    private static final CardTypeAssignment cardTypeAssigner = new CardTypeAssignment(CardType.CREATURE, CardType.LAND);

    YunasDecisionTarget() {
        super(0, 2, StaticFilters.FILTER_CARD_CREATURE_OR_LAND);
    }

    private YunasDecisionTarget(final YunasDecisionTarget target) {
        super(target);
    }

    @Override
    public YunasDecisionTarget copy() {
        return new YunasDecisionTarget(this);
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
