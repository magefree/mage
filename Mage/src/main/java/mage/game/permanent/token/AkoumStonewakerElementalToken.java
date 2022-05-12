package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class AkoumStonewakerElementalToken extends TokenImpl {

    public AkoumStonewakerElementalToken() {
        super("Elemental Token", "3/1 red Elemental creature token with trample and haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(3);
        toughness = new MageInt(1);
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
        availableImageSetCodes.addAll(Arrays.asList("BFZ", "MH1"));
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("BFZ")) {
            setTokenType(2);
        }

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("MH1")) {
            setTokenType(2);
        }
    }

    public AkoumStonewakerElementalToken(final AkoumStonewakerElementalToken token) {
        super(token);
    }

    public AkoumStonewakerElementalToken copy() {
        return new AkoumStonewakerElementalToken(this);
    }
}

