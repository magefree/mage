package mage.cards.w;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.filter.StaticFilters;

/**
 *
 * @author nandmp
 */
public final class WorldsWithinWorlds extends CardImpl {

    public WorldsWithinWorlds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{G}{U}");


        // Exile all creatures. Each player may put any number of creature cards from their hand onto the battlefield. Then put all cards exiled this way into their owners' hands. Exile Worlds Within Worlds.
        this.getSpellAbility().addEffect(new WorldsWithinWorldsEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());

    }

    private WorldsWithinWorlds(final WorldsWithinWorlds card) {
        super(card);
    }

    @Override
    public WorldsWithinWorlds copy() {
        return new WorldsWithinWorlds(this);
    }
}

class WorldsWithinWorldsEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard(
            "put any number of creature cards from your hand onto the battlefield"
    );

    WorldsWithinWorldsEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile all creatures. Each player may put any number of creature cards from their hand onto the battlefield. "
                + "Then put all cards exiled this way into their owners' hands";
    }

    private WorldsWithinWorldsEffect(final WorldsWithinWorldsEffect effect) {
        super(effect);
    }

    @Override
    public WorldsWithinWorldsEffect copy() {
        return new WorldsWithinWorldsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Cards exiledCards = new CardsImpl();
        for (Permanent creature : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game
        )) {
            if (!controller.moveCards(creature, Zone.EXILED, source, game)) {
                continue;
            }
            Card card = game.getCard(creature.getId());
            if (card != null && game.getState().getZone(card.getId()) == Zone.EXILED) {
                exiledCards.add(card);
            }
        }

        Cards toBattlefield = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            TargetCardInHand target = new TargetCardInHand(0, Integer.MAX_VALUE, filter);
            player.chooseTarget(Outcome.PutCreatureInPlay, player.getHand(), target, source, game);
            toBattlefield.addAll(target.getTargets());
        }
        controller.moveCards(toBattlefield.getCards(game), Zone.BATTLEFIELD, source, game, false, false, true, null);

        for (Card card : exiledCards.getCards(game)) {
            Player owner = game.getPlayer(card.getOwnerId());
            if (owner != null) {
                owner.moveCards(card, Zone.HAND, source, game);
            }
        }

        return true;
    }
}
