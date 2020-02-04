

package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;

/**
 *
 * @author LoneFox
 */
public final class SoldierTokenWithHaste extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("GTC", "MM3"));
    }

    public SoldierTokenWithHaste() {
        super("Soldier", "1/1 red and white Soldier creature token with haste");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setRed(true);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(HasteAbility.getInstance());
    }


    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("MM3")) {
            setTokenType(2);
        }
    }

    public SoldierTokenWithHaste(final SoldierTokenWithHaste token) {
        super(token);
    }

    @Override
    public SoldierTokenWithHaste copy() {
        return new SoldierTokenWithHaste(this); //To change body of generated methods, choose Tools | Templates.
    }
}
