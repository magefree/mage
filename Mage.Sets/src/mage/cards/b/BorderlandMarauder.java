package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class BorderlandMarauder extends CardImpl {

    public BorderlandMarauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.HUMAN, SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever Borderland Marauder attacks, it gets +2/+0 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(
                new BoostSourceEffect(2, 0, Duration.EndOfTurn, "it"), false
        ));
    }

    private BorderlandMarauder(final BorderlandMarauder card) {
        super(card);
    }

    @Override
    public BorderlandMarauder copy() {
        return new BorderlandMarauder(this);
    }
}
