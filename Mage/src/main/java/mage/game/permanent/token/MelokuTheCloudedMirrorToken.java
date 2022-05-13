package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class MelokuTheCloudedMirrorToken extends TokenImpl {

    public MelokuTheCloudedMirrorToken() {
        super("Illusion Token", "1/1 blue Illusion creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.ILLUSION);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = Arrays.asList("CHK", "MMA", "MH1", "CMR");
    }

    public MelokuTheCloudedMirrorToken(final MelokuTheCloudedMirrorToken token) {
        super(token);
    }

    public MelokuTheCloudedMirrorToken copy() {
        return new MelokuTheCloudedMirrorToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
    }
}
