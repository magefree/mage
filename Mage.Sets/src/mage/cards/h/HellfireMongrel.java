
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class HellfireMongrel extends CardImpl {

    public HellfireMongrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.DOG);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of each opponent's upkeep, if that player has two or fewer cards in hand, Hellfire Mongrel deals 2 damage to that player.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), TargetController.OPPONENT, false, true),
                (Condition)new CardsInHandCondition(ComparisonType.FEWER_THAN, 3, null, TargetController.ACTIVE),
                "At the beginning of each opponent's upkeep, if that player has two or fewer cards in hand, {this} deals 2 damage to that player."
        ));
    }

    private HellfireMongrel(final HellfireMongrel card) {
        super(card);
    }

    @Override
    public HellfireMongrel copy() {
        return new HellfireMongrel(this);
    }
}
