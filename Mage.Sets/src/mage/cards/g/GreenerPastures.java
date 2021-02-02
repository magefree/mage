
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.token.SaprolingToken;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class GreenerPastures extends CardImpl {

    public GreenerPastures(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // At the beginning of each player's upkeep, if that player controls more lands than each other player, the player creates a 1/1 green Saproling creature token.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        Zone.BATTLEFIELD,
                        new CreateTokenTargetEffect(new SaprolingToken()),
                        TargetController.ANY, false, true
                ),
                ActivePlayerMostLandsCondition.instance,
                "At the beginning of each player's upkeep, "
                + "if that player controls more lands than each other player, "
                + "the player creates a 1/1 green Saproling creature token."
        ));
    }

    private GreenerPastures(final GreenerPastures card) {
        super(card);
    }

    @Override
    public GreenerPastures copy() {
        return new GreenerPastures(this);
    }
}

enum ActivePlayerMostLandsCondition implements Condition {

    instance;
    private static final FilterLandPermanent filter = new FilterLandPermanent();

    @Override
    public boolean apply(Game game, Ability source) {
        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        if (activePlayer == null) {
            return false;
        }
        int landCount = game.getBattlefield().getAllActivePermanents(filter, activePlayer.getId(), game).size();
        if (landCount == 0) {
            return false;
        }
        for (UUID playerId : game.getPlayerList()) {
            if (!playerId.equals(activePlayer.getId())) {
                if (game.getBattlefield().getAllActivePermanents(filter, playerId, game).size() >= landCount) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "that player controls more lands than each other player";
    }

}
