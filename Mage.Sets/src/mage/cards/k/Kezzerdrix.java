
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.CreatureCountCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author jeffwadsworth
 */
public final class Kezzerdrix extends CardImpl {

    public Kezzerdrix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.RABBIT);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // At the beginning of your upkeep, if your opponents control no creatures, Kezzerdrix deals 4 damage to you.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new DamageControllerEffect(4), TargetController.YOU, false),
                new CreatureCountCondition(0, TargetController.OPPONENT),
                "At the beginning of your upkeep, if your opponents control no creatures, {this} deals 4 damage to you."));
    }

    private Kezzerdrix(final Kezzerdrix card) {
        super(card);
    }

    @Override
    public Kezzerdrix copy() {
        return new Kezzerdrix(this);
    }
}
