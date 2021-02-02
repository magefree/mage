
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author michael.napoleon@gmail.com
 */
public final class LlanowarKnight extends CardImpl {

    public LlanowarKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{W}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Protection from black
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));
        
    }

    private LlanowarKnight(final LlanowarKnight card) {
        super(card);
    }

    @Override
    public LlanowarKnight copy() {
        return new LlanowarKnight(this);
    }
}
