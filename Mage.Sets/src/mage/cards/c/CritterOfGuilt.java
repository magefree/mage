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
public final class CritterOfGuilt extends CardImpl {

    public CritterOfGuilt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}{R}");

        //the 3 subtypes are extremely relevant 
        this.subtype.add(SubType.OTTER);
        this.subtype.add(SubType.SLUG);
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // {1}, Exile the card, land taps for the 3 colors, cast the card later and land loses ability
        this.addAbility(new GiveManaAbilityAndCastSourceAbility("UBR",1));
    }

    private CritterOfGuilt(final CritterOfGuilt card) {
        super(card);
    }

    @Override
    public CritterOfGuilt copy() {
        return new CritterOfGuilt(this);
    }
}