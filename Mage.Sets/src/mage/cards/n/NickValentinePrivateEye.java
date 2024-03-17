package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class NickValentinePrivateEye extends CardImpl {

    public NickValentinePrivateEye(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SYNTH);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Nick Valentine, Private Eye can't be blocked except by artifact creatures.
        // Whenever Nick Valentine or another artifact creature you control dies, you may investigate.
    }

    private NickValentinePrivateEye(final NickValentinePrivateEye card) {
        super(card);
    }

    @Override
    public NickValentinePrivateEye copy() {
        return new NickValentinePrivateEye(this);
    }
}
