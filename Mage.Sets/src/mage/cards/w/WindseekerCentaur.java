
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class WindseekerCentaur extends CardImpl {

    public WindseekerCentaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.CENTAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
    }

    private WindseekerCentaur(final WindseekerCentaur card) {
        super(card);
    }

    @Override
    public WindseekerCentaur copy() {
        return new WindseekerCentaur(this);
    }
}
