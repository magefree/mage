
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class VoiceOfReason extends CardImpl {

    public VoiceOfReason(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // protection from blue
        this.addAbility(ProtectionAbility.from(ObjectColor.BLUE));
    }

    private VoiceOfReason(final VoiceOfReason card) {
        super(card);
    }

    @Override
    public VoiceOfReason copy() {
        return new VoiceOfReason(this);
    }
}
