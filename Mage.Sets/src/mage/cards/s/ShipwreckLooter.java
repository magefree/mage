
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.watchers.common.PlayerAttackedWatcher;

/**
 *
 * @author TheElk801
 */
public final class ShipwreckLooter extends CardImpl {

    public ShipwreckLooter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Raid - When Shipwreck Looter enters the battlefield,if you attacked with a creature this turn, you may draw a card. If you do, discard a card.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DrawDiscardControllerEffect(1, 1, true)),
                RaidCondition.instance,
                "<i>Raid</i> &mdash; When {this} enters the battlefield, if you attacked with a creature this turn, you may draw a card. If you do, discard a card.");
        this.addAbility(ability, new PlayerAttackedWatcher());
    }

    public ShipwreckLooter(final ShipwreckLooter card) {
        super(card);
    }

    @Override
    public ShipwreckLooter copy() {
        return new ShipwreckLooter(this);
    }
}
