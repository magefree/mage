
package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class OathOfDruids extends CardImpl {

    public OathOfDruids(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // At the beginning of each player's upkeep, that player chooses target player who controls more creatures than they do and is their opponent.
        // The first player may reveal cards from the top of their library until they reveal a creature card.
        // If they do, that player puts that card onto the battlefield and all other cards revealed this way into their graveyard.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new OathOfDruidsEffect(), TargetController.ANY, false);
        ability.setTargetAdjuster(OathOfDruidsAdjuster.instance);
        this.addAbility(ability);
    }

    private OathOfDruids(final OathOfDruids card) {
        super(card);
    }

    @Override
    public OathOfDruids copy() {
        return new OathOfDruids(this);
    }
}

enum OathOfDruidsAdjuster implements TargetAdjuster {
    instance;
    private static final FilterPlayer filter = new FilterPlayer();

    static {
        filter.add(new OathOfDruidsPredicate());
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

class OathOfDruidsPredicate implements ObjectSourcePlayerPredicate<Player> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        Player targetPlayer = input.getObject();
        //Get active input.playerId because adjust target is used after canTarget function
        UUID activePlayerId = game.getActivePlayerId();
        if (targetPlayer == null || activePlayerId == null) {
            return false;
        }
        if (!targetPlayer.hasOpponent(activePlayerId, game)) {
            return false;
        }
        int countTargetPlayer = game.getBattlefield().countAll(filter, targetPlayer.getId(), game);
        int countActivePlayer = game.getBattlefield().countAll(filter, activePlayerId, game);

        return countTargetPlayer > countActivePlayer;
    }

    @Override
    public String toString() {
        return "player who controls more creatures than they do and is their opponent";
    }
}

class OathOfDruidsEffect extends OneShotEffect {

    public OathOfDruidsEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "that player chooses target player who controls more creatures than they do and is their opponent. "
                + "The first player may reveal cards from the top of their library until they reveal a creature card. "
                + "If they do, that player puts that card onto the battlefield and all other cards revealed this way into their graveyard";
    }

    public OathOfDruidsEffect(OathOfDruidsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(game.getActivePlayerId());
        if (controller == null) {
            return false;
        }
        Cards revealed = new CardsImpl();
        Card selectedCard = null;
        Cards notSelectedCards = new CardsImpl();
        if (!controller.chooseUse(Outcome.Benefit, "Use this ability?", source, game)) {
            return true;
        }
        //The first player may reveal cards from the top of their library
        for (Card card : controller.getLibrary().getCards(game)) {
            revealed.add(card);
            // until they reveal a creature card.
            if (card.isCreature(game)) {
                selectedCard = card;
                break;
            } else {
                notSelectedCards.add(card);
            }
        }
        controller.revealCards(source, revealed, game);

        //If they do, that player puts that card onto the battlefield
        if (selectedCard != null) {
            controller.moveCards(selectedCard, Zone.BATTLEFIELD, source, game);
        }
        // and all other cards revealed this way into their graveyard
        controller.moveCards(notSelectedCards, Zone.GRAVEYARD, source, game);
        return true;
    }

    @Override
    public OathOfDruidsEffect copy() {
        return new OathOfDruidsEffect(this);
    }
}
