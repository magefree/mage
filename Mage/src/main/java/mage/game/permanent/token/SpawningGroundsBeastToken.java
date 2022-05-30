package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class SpawningGroundsBeastToken extends TokenImpl {

    public SpawningGroundsBeastToken() {
        super("Beast Token", "5/5 green Beast creature token with trample");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.BEAST);
        power = new MageInt(5);
        toughness = new MageInt(5);

        this.addAbility(TrampleAbility.getInstance());

        availableImageSetCodes = Arrays.asList("C18");
    }

    public SpawningGroundsBeastToken(final SpawningGroundsBeastToken token) {
        super(token);
    }

    public SpawningGroundsBeastToken copy() {
        return new SpawningGroundsBeastToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C18")) {
            this.setTokenType(2);
        }
    }
}
