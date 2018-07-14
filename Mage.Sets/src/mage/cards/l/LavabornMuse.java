
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author ilcartographer
 */
public final class LavabornMuse extends CardImpl {

    public LavabornMuse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of each opponent's upkeep, if that player has two or fewer cards in hand, Lavaborn Muse deals 3 damage to him or her.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new DamageTargetEffect(3), TargetController.OPPONENT, false, true),
                new CardsInActivePlayersHandCondition(),
                "At the beginning of each opponent's upkeep, if that player has two or fewer cards in hand, {this} deals 3 damage to him or her."));
    }

    public LavabornMuse(final LavabornMuse card) {
        super(card);
    }

    @Override
    public LavabornMuse copy() {
        return new LavabornMuse(this);
    }
}

// TODO: Figure out CardsInHandCondition parameters and use that instead of rewriting this
// TODO: Update HellfireMongrel, ShriekingAffliction to use the CardsInHandCondition?
class CardsInActivePlayersHandCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        return player != null && player.getHand().size() <= 2;
    }
}
