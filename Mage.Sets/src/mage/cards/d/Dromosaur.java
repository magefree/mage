package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksOrBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 *
 * @author awjackson
 */
public final class Dromosaur extends CardImpl {

    public Dromosaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.DINOSAUR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Dromosaur blocks or becomes blocked, it gets +2/-2 until end of turn.
        this.addAbility(new BlocksOrBlockedSourceTriggeredAbility(new BoostSourceEffect(2, -2, Duration.EndOfTurn, "it")));
    }

    private Dromosaur(final Dromosaur card) {
        super(card);
    }

    @Override
    public Dromosaur copy() {
        return new Dromosaur(this);
    }
}
