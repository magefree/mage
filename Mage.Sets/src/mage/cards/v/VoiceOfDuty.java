
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
public final class VoiceOfDuty extends CardImpl {

    public VoiceOfDuty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // protection from green
        this.addAbility(ProtectionAbility.from(ObjectColor.GREEN));
    }

    private VoiceOfDuty(final VoiceOfDuty card) {
        super(card);
    }

    @Override
    public VoiceOfDuty copy() {
        return new VoiceOfDuty(this);
    }
}
