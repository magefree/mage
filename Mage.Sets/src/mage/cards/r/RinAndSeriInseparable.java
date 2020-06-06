package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author htrajan
 */
public final class RinAndSeriInseparable extends CardImpl {

    public RinAndSeriInseparable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}{W}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DOG);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you cast a Dog spell, create a 1/1 green Cat creature token.
        // Whenever you cast a Cat spell, create a 1/1 white Dog creature token.
        // {R}{G}{W}: Rin and Seri, Inseparable deals damage to any target equal to the number of Dogs you control. You gain life equal to the number of Cats you control.
    }

    private RinAndSeriInseparable(final RinAndSeriInseparable card) {
        super(card);
    }

    @Override
    public RinAndSeriInseparable copy() {
        return new RinAndSeriInseparable(this);
    }
}
