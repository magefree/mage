

package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.InfectAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Viserion
 */
public final class PhyrexianCrusader extends CardImpl {

    public PhyrexianCrusader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike,
        this.addAbility(FirstStrikeAbility.getInstance());
        // protection from red and from white
        this.addAbility(ProtectionAbility.from(ObjectColor.RED, ObjectColor.WHITE));
        // Infect
        this.addAbility(InfectAbility.getInstance());
    }

    private PhyrexianCrusader(final PhyrexianCrusader card) {
        super(card);
    }

    @Override
    public PhyrexianCrusader copy() {
        return new PhyrexianCrusader(this);
    }

}
