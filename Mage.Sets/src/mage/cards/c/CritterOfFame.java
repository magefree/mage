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
public final class CritterOfFame extends CardImpl {

    public CritterOfFame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}{W}");

        //the 3 subtypes are extremely relevant 
        this.subtype.add(SubType.OTTER);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // {1}, Exile the card, land taps for the 3 colors, cast the card later and land loses ability
        this.addAbility(new GiveManaAbilityAndCastSourceAbility("URW",1));
    }

    private CritterOfFame(final CritterOfFame card) {
        super(card);
    }

    @Override
    public CritterOfFame copy() {
        return new CritterOfFame(this);
    }
}