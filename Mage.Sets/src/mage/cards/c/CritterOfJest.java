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
public final class CritterOfJest extends CardImpl {

    public CritterOfJest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}{G}");

        //the 3 subtypes are extremely relevant 
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // {1}, Exile the card, land taps for the 3 colors, cast the card later and land loses ability
        this.addAbility(new GiveManaAbilityAndCastSourceAbility("WBG",1));
    }

    private CritterOfJest(final CritterOfJest card) {
        super(card);
    }

    @Override
    public CritterOfJest copy() {
        return new CritterOfJest(this);
    }
}