
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class ZodiacPig extends CardImpl {

    public ZodiacPig(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.BOAR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Swampwalk
        this.addAbility(new SwampwalkAbility());
    }

    private ZodiacPig(final ZodiacPig card) {
        super(card);
    }

    @Override
    public ZodiacPig copy() {
        return new ZodiacPig(this);
    }
}
