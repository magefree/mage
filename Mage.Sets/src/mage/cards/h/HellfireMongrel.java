
package mage.cards.h;

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
 * @author North
 */
public final class HellfireMongrel extends CardImpl {

    public HellfireMongrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.HOUND);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of each opponent's upkeep, if that player has two or fewer cards in hand, Hellfire Mongrel deals 2 damage to him or her.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), TargetController.OPPONENT, false, true),
                new CardsInActivePlayersHandCondition(),
                "At the beginning of each opponent's upkeep, if that player has two or fewer cards in hand, {this} deals 2 damage to him or her."
        ));
    }

    public HellfireMongrel(final HellfireMongrel card) {
        super(card);
    }

    @Override
    public HellfireMongrel copy() {
        return new HellfireMongrel(this);
    }
}

class CardsInActivePlayersHandCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        return player != null && player.getHand().size() <= 2;
    }
}
