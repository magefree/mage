
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ProvokeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author emerald000
 */
public final class Brontotherium extends CardImpl {

    public Brontotherium(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // Provoke
        this.addAbility(new ProvokeAbility());
    }

    private Brontotherium(final Brontotherium card) {
        super(card);
    }

    @Override
    public Brontotherium copy() {
        return new Brontotherium(this);
    }
}
