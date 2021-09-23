
package mage.cards.o;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPlayer;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OathOfGhouls extends CardImpl {

    public OathOfGhouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // At the beginning of each player's upkeep, that player chooses target player whose graveyard has fewer creature cards in it than their graveyard does and is their opponent. The first player may return a creature card from their graveyard to their hand.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new OathOfGhoulsEffect(), TargetController.ANY, false);
        ability.setTargetAdjuster(OathOfGhoulsAdjuster.instance);
        this.addAbility(ability);
    }

    private OathOfGhouls(final OathOfGhouls card) {
        super(card);
    }

    @Override
    public OathOfGhouls copy() {
        return new OathOfGhouls(this);
    }
}

enum OathOfGhoulsAdjuster implements TargetAdjuster {
    instance;
    private static final FilterPlayer filter = new FilterPlayer();

    static {
        filter.add(new OathOfGhoulsPredicate());
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        if (activePlayer != null) {
            ability.getTargets().clear();
            TargetPlayer target = new TargetPlayer(1, 1, false, filter);
            target.setTargetController(activePlayer.getId());
            ability.getTargets().add(target);
        }
    }
}

class OathOfGhoulsPredicate implements ObjectSourcePlayerPredicate<Player> {

    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        Player targetPlayer = input.getObject();
        Player firstPlayer = game.getPlayer(game.getActivePlayerId());
        if (targetPlayer == null
                || firstPlayer == null
                || !firstPlayer.hasOpponent(targetPlayer.getId(), game)) {
            return false;
        }
        int countGraveyardTargetPlayer = targetPlayer.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game).size();
        int countGraveyardFirstPlayer = firstPlayer.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game).size();

        return countGraveyardTargetPlayer < countGraveyardFirstPlayer;
    }

    @Override
    public String toString() {
        return "player whose graveyard has fewer creature cards in it than their graveyard does and is their opponent";
    }
}

class OathOfGhoulsEffect extends OneShotEffect {

    public OathOfGhoulsEffect() {
        super(Outcome.Benefit);
        staticText = "that player chooses target player whose graveyard has fewer creature cards in it than their graveyard does and is their opponent. The first player may return a creature card from their graveyard to their hand";
    }

    public OathOfGhoulsEffect(OathOfGhoulsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source.getSourceId());
        Player firstPlayer = game.getPlayer(game.getActivePlayerId());
        if (sourceObject == null || firstPlayer == null) {
            return false;
        }
        FilterCard filter = new FilterCreatureCard("creature card");
        filter.add(new OwnerIdPredicate(firstPlayer.getId()));
        Target target = new TargetCardInGraveyard(filter);
        target.setNotTarget(true);
        if (target.canChoose(source.getSourceId(), firstPlayer.getId(), game)
                && firstPlayer.chooseUse(outcome, "Return a creature card from your graveyard to your hand?", source, game)
                && firstPlayer.chooseTarget(Outcome.ReturnToHand, target, source, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                firstPlayer.moveCards(card, Zone.HAND, source, game);
            }
        }
        return true;
    }

    @Override
    public OathOfGhoulsEffect copy() {
        return new OathOfGhoulsEffect(this);
    }
}
