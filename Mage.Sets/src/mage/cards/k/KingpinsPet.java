
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ExtortAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class KingpinsPet extends CardImpl {

    public KingpinsPet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{B}");
        this.subtype.add(SubType.THRULL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Extort
        this.addAbility(new ExtortAbility());
    }

    private KingpinsPet(final KingpinsPet card) {
        super(card);
    }

    @Override
    public KingpinsPet copy() {
        return new KingpinsPet(this);
    }
}
