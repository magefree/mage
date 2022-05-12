package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class TemptWithImmortality extends CardImpl {

    public TemptWithImmortality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Tempting offer - Return a creature card from your graveyard to the battlefield. Each opponent may return a creature card from their graveyard to the battlefield. For each opponent who does, return a creature card from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new TemptWithImmortalityEffect());
    }

    private TemptWithImmortality(final TemptWithImmortality card) {
        super(card);
    }

    @Override
    public TemptWithImmortality copy() {
        return new TemptWithImmortality(this);
    }
}

class TemptWithImmortalityEffect extends OneShotEffect {

    public TemptWithImmortalityEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "<i>Tempting offer</i> &mdash; Return a creature card from your graveyard to the battlefield. Each opponent may return a creature card from their graveyard to the battlefield. For each opponent who does, return a creature card from your graveyard to the battlefield";

    }

    public TemptWithImmortalityEffect(final TemptWithImmortalityEffect effect) {
        super(effect);
    }

    @Override
    public TemptWithImmortalityEffect copy() {
        return new TemptWithImmortalityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            returnCreatureFromGraveToBattlefield(controller, source, game);

            int opponentsReturnedCreatures = 0;
            for (UUID playerId : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(playerId);
                if (opponent != null) {
                    FilterCard filter = new FilterCreatureCard("creature card from your graveyard");
                    filter.add(new OwnerIdPredicate(opponent.getId()));
                    Target targetCardOpponent = new TargetCardInGraveyard(filter);

                    if (targetCardOpponent.canChoose(opponent.getId(), source, game)) {
                        if (opponent.chooseUse(outcome, "Return a creature card from your graveyard to the battlefield?", source, game)) {
                            if (opponent.chooseTarget(outcome, targetCardOpponent, source, game)) {
                                Card card = game.getCard(targetCardOpponent.getFirstTarget());
                                if (card != null) {
                                    opponentsReturnedCreatures++;
                                    opponent.moveCards(card, Zone.BATTLEFIELD, source, game);
                                }
                            }
                        }
                    }
                }
            }
            if (opponentsReturnedCreatures > 0) {
                for (int i = 0; i < opponentsReturnedCreatures; i++) {
                    returnCreatureFromGraveToBattlefield(controller, source, game);
                }
            }
            return true;
        }

        return false;
    }

    private boolean returnCreatureFromGraveToBattlefield(Player player, Ability source, Game game) {
        Target target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE);
        target.setNotTarget(false);
        if (target.canChoose(source.getControllerId(), source, game)) {
            if (player.chooseTarget(outcome, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    return player.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            }
        }
        return false;
    }
}
