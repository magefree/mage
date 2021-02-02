
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class VulpineGoliath extends CardImpl {

    public VulpineGoliath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.FOX);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private VulpineGoliath(final VulpineGoliath card) {
        super(card);
    }

    @Override
    public VulpineGoliath copy() {
        return new VulpineGoliath(this);
    }
}
