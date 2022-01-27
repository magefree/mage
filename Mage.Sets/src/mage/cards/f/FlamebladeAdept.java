
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CycleOrDiscardControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author fireshoes
 */
public final class FlamebladeAdept extends CardImpl {

    public FlamebladeAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.JACKAL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever you cycle or discard a card, Flameblade Adept gets +1/+0 until end of turn.
        this.addAbility(new CycleOrDiscardControllerTriggeredAbility(new BoostSourceEffect(1, 0, Duration.EndOfTurn)));
    }

    private FlamebladeAdept(final FlamebladeAdept card) {
        super(card);
    }

    @Override
    public FlamebladeAdept copy() {
        return new FlamebladeAdept(this);
    }
}
