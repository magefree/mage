package mage.cards.i;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CommanderCardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ImposingGrandeur extends CardImpl {

    public ImposingGrandeur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Each player may discard their hand and draw cards equal to the greatest mana value of a commander they own on the battlefield or in the command zone.
        this.getSpellAbility().addEffect(new ImposingGrandeurEffect());
    }

    private ImposingGrandeur(final ImposingGrandeur card) {
        super(card);
    }

    @Override
    public ImposingGrandeur copy() {
        return new ImposingGrandeur(this);
    }
}

class ImposingGrandeurEffect extends OneShotEffect {

    ImposingGrandeurEffect() {
        super(Outcome.DrawCard);
        staticText = "each player may discard their hand and draw cards equal to " +
                "the greatest mana value of a commander they own on the battlefield or in the command zone";
    }

    private ImposingGrandeurEffect(final ImposingGrandeurEffect effect) {
        super(effect);
    }

    @Override
    public ImposingGrandeurEffect copy() {
        return new ImposingGrandeurEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, Integer> map = new HashMap<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int maxValue = game
                    .getCommanderCardsFromAnyZones(
                            player, CommanderCardType.ANY,
                            Zone.BATTLEFIELD, Zone.COMMAND
                    )
                    .stream()
                    .mapToInt(MageObject::getManaValue)
                    .max()
                    .orElse(0);
            map.put(playerId, maxValue);
        }
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            int amount = map.getOrDefault(playerId, 0);
            if (player != null && player.chooseUse(
                    outcome, "Discard your hand and draw "
                            + amount + " cards?", source, game
            )) {
                player.discard(player.getHand(), false, source, game);
                player.drawCards(amount, source, game);
            }
        }
        return true;
    }
}
