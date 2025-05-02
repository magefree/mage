

package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */


public final class AscendedLawmage extends CardImpl {

    public AscendedLawmage (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);


        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

    }

    private AscendedLawmage(final AscendedLawmage card) {
        super(card);
    }

    @Override
    public AscendedLawmage copy() {
        return new AscendedLawmage(this);
    }

}
