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
public final class CritterOfBravery extends CardImpl {

    public CritterOfBravery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}{U}");

        //the 3 subtypes are extremely relevant 
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.FISH);
        this.subtype.add(SubType.SLUG);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // {1}, Exile the card, land taps for the 3 colors, cast the card later and land loses ability
        this.addAbility(new GiveManaAbilityAndCastSourceAbility("BGU",1));
    }

    private CritterOfBravery(final CritterOfBravery card) {
        super(card);
    }

    @Override
    public CritterOfBravery copy() {
        return new CritterOfBravery(this);
    }
}