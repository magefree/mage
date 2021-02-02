

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
 * @author Backfir3
 */
public final class VoiceOfGrace extends CardImpl {

    public VoiceOfGrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));
    }

    private VoiceOfGrace(final VoiceOfGrace card) {
        super(card);
    }

    @Override
    public VoiceOfGrace copy() {
        return new VoiceOfGrace(this);
    }

}
