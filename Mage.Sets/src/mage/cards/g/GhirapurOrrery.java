
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.IntCompareCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class GhirapurOrrery extends CardImpl {

    public GhirapurOrrery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // Each player may play an additional land on each of their turns.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayAdditionalLandsAllEffect()));

        // At the beginning of each player's upkeep, if that player has no cards in hand, that player draws three cards.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new DrawCardTargetEffect(3), TargetController.ANY, false, true),
                new GhirapurOrreryCondition(),
                "At the beginning of each player's upkeep, if that player has no cards in hand, that player draws three cards."));
    }

    private GhirapurOrrery(final GhirapurOrrery card) {
        super(card);
    }

    @Override
    public GhirapurOrrery copy() {
        return new GhirapurOrrery(this);
    }
}

class GhirapurOrreryCondition extends IntCompareCondition {

    GhirapurOrreryCondition() {
        super(ComparisonType.EQUAL_TO, 0);
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        if (activePlayer != null) {
            return activePlayer.getHand().size();
        }
        return 0;
    }
}
