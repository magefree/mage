
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CycleOrDiscardControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author Styxo
 */
public final class HekmaSentinels extends CardImpl {

    public HekmaSentinels(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you cycle or discard a card, Hekma Sentinels gets +1/+1 until end of turn.
        this.addAbility(new CycleOrDiscardControllerTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn)));
    }

    private HekmaSentinels(final HekmaSentinels card) {
        super(card);
    }

    @Override
    public HekmaSentinels copy() {
        return new HekmaSentinels(this);
    }
}
