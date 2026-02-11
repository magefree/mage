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
public final class CritterOfHeart extends CardImpl {

    public CritterOfHeart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{U}{B}");

        //the 3 subtypes are extremely relevant 
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SLUG);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // {1}, Exile the card, land taps for the 3 colors, cast the card later and land loses ability
        this.addAbility(new GiveManaAbilityAndCastSourceAbility("WUB",1));
    }

    private CritterOfHeart(final CritterOfHeart card) {
        super(card);
    }

    @Override
    public CritterOfHeart copy() {
        return new CritterOfHeart(this);
    }
}