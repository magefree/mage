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
public final class CritterOfIntent extends CardImpl {

    public CritterOfIntent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{W}{U}");

        //the 3 subtypes are extremely relevant 
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.FISH);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // {1}, Exile the card, land taps for the 3 colors, cast the card later and land loses ability
        this.addAbility(new GiveManaAbilityAndCastSourceAbility("GWU",1));
    }

    private CritterOfIntent(final CritterOfIntent card) {
        super(card);
    }

    @Override
    public CritterOfIntent copy() {
        return new CritterOfIntent(this);
    }
}