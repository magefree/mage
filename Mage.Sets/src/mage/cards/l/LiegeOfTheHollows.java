package mage.cards.l;

import java.util.HashMap;
import java.util.Map;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.SquirrelToken;
import mage.players.Player;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author arcox
 */
public final class LiegeOfTheHollows extends CardImpl {

    public LiegeOfTheHollows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Liege of the Hollows dies, each player may pay any amount of mana.
        // Then each player creates a number of 1/1 green Squirrel creature tokens 
        // equal to the amount of mana they paid this way.
        this.addAbility(new DiesSourceTriggeredAbility(new LiegeOfTheHollowsEffect()));
    }

    private LiegeOfTheHollows(final LiegeOfTheHollows card) {
        super(card);
    }

    @Override
    public LiegeOfTheHollows copy() {
        return new LiegeOfTheHollows(this);
    }
}

class LiegeOfTheHollowsEffect extends OneShotEffect {

    public LiegeOfTheHollowsEffect() {
        super(Outcome.Detriment);
        this.staticText = "each player may pay any amount of mana. Then each player creates a number "
                + "of 1/1 green Squirrel creature tokens equal to the amount of mana they paid this way";
    }

    private LiegeOfTheHollowsEffect(final LiegeOfTheHollowsEffect effect) {
        super(effect);
    }

    @Override
    public LiegeOfTheHollowsEffect copy() {
        return new LiegeOfTheHollowsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Map<UUID, Integer> paidMana = new HashMap<>();
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    paidMana.put(player.getId(), ManaUtil.playerPaysXGenericMana(false, 
                            "Liege of the Hollows", player, source, game));
                }
            }
            // create tokens
            SquirrelToken token = new SquirrelToken();
            for (Map.Entry<UUID, Integer> entry
                    : paidMana.entrySet()) {
                Player player = game.getPlayer(entry.getKey());
                if (player != null) {
                    token.putOntoBattlefield(entry.getValue(), game, source, player.getId());
                }
            }
            game.getState().processAction(game);
            
            // prevent undo
            controller.resetStoredBookmark(game);
            return true;
        }
        return false;
    }
}
