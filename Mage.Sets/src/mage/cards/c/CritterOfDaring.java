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
public final class CritterOfDaring extends CardImpl {

    public CritterOfDaring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}{B}");

        //the 3 subtypes are extremely relevant 
        this.subtype.add(SubType.DOG);
        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // {1}, Exile the card, land taps for the 3 colors, cast the card later and land loses ability
        this.addAbility(new GiveManaAbilityAndCastSourceAbility("RWB",1));
    }

    private CritterOfDaring(final CritterOfDaring card) {
        super(card);
    }

    @Override
    public CritterOfDaring copy() {
        return new CritterOfDaring(this);
    }
}