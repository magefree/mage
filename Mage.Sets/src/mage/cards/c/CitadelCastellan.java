
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.RenownAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class CitadelCastellan extends CardImpl {

    public CitadelCastellan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Renown 2
        this.addAbility(new RenownAbility(2));
    }

    private CitadelCastellan(final CitadelCastellan card) {
        super(card);
    }

    @Override
    public CitadelCastellan copy() {
        return new CitadelCastellan(this);
    }
}
