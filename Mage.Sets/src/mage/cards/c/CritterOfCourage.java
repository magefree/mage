package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.GiveManaAbilityAndCastSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import java.util.UUID;

/**
 * @author ChesseTheWasp
 */
public final class CritterOfCourage extends CardImpl {

    public CritterOfCourage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}{W}");

        //the 3 subtypes are extremely relevant 
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // {1}, Exile the card, land taps for the 3 colors, cast the card later and land loses ability
        this.addAbility(new GiveManaAbilityAndCastSourceAbility("RGW",1));
    }

    private CritterOfCourage(final CritterOfCourage card) {
        super(card);
    }

    @Override
    public CritterOfCourage copy() {
        return new CritterOfCourage(this);
    }
}