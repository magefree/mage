
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Backfir3
 */
public final class DefenderOfLaw extends CardImpl {

    public DefenderOfLaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(FlashAbility.getInstance());
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));
    }

    private DefenderOfLaw(final DefenderOfLaw card) {
        super(card);
    }

    @Override
    public DefenderOfLaw copy() {
        return new DefenderOfLaw(this);
    }
}
