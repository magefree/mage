package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

import java.util.Arrays;

/**
 * @author LoneFox
 */
public final class FaerieRogueToken extends TokenImpl {

    public FaerieRogueToken() {
        super("Faerie Rogue Token", "1/1 black Faerie Rogue creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.FAERIE);
        subtype.add(SubType.ROGUE);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = Arrays.asList("MOR", "MM2", "ZNC", "UMA", "SLD", "2X2");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("SLD")) {
            setTokenType(RandomUtil.nextInt(4) + 1);
        }
    }

    public FaerieRogueToken(final FaerieRogueToken token) {
        super(token);
    }

    public FaerieRogueToken copy() {
        return new FaerieRogueToken(this);
    }
}
