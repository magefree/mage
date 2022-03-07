
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class BrambleCreeper extends CardImpl {

    public BrambleCreeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);
        // Whenever Bramble Creeper attacks, it gets +5/+0 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(5, 0, Duration.EndOfTurn).setText("it gets +5/+0 until end of turn"), false));
    }

    private BrambleCreeper(final BrambleCreeper card) {
        super(card);
    }

    @Override
    public BrambleCreeper copy() {
        return new BrambleCreeper(this);
    }
}
