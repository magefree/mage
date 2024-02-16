
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class ZephyrSpirit extends CardImpl {

    public ZephyrSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(6);

        // When Zephyr Spirit blocks, return it to its owner's hand.
        this.addAbility(
                new BlocksSourceTriggeredAbility(
                        new ReturnToHandSourceEffect(true).setText("return it to its owner's hand")
                ).setTriggerPhrase("When {this} blocks, ")
        );
    }

    private ZephyrSpirit(final ZephyrSpirit card) {
        super(card);
    }

    @Override
    public ZephyrSpirit copy() {
        return new ZephyrSpirit(this);
    }
}
