package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author spjspj
 */
public final class VoiceOfResurgenceToken extends TokenImpl {

    public VoiceOfResurgenceToken() {
        super("Elemental Token", "X/X green and white Elemental creature with with \"This creature's power and toughness are each equal to the number of creatures you control.");
        setOriginalExpansionSetCode("DGM");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        color.setWhite(true);
        subtype.add(SubType.ELEMENTAL);

        power = new MageInt(0);
        toughness = new MageInt(0);

        // This creature's power and toughness are each equal to the number of creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SetPowerToughnessSourceEffect(
                CreaturesYouControlCount.instance, Duration.EndOfGame)));
    }

    public VoiceOfResurgenceToken(final VoiceOfResurgenceToken token) {
        super(token);
    }

    public VoiceOfResurgenceToken copy() {
        return new VoiceOfResurgenceToken(this);
    }
}
