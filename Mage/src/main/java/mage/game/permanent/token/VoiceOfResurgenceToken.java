
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author spjspj
 */
public final class VoiceOfResurgenceToken extends TokenImpl {

    public VoiceOfResurgenceToken() {
        super("Elemental", "X/X green and white Elemental creature with with \"This creature's power and toughness are each equal to the number of creatures you control.");
        setOriginalExpansionSetCode("DGM");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        color.setWhite(true);
        subtype.add(SubType.ELEMENTAL);

        power = new MageInt(0);
        toughness = new MageInt(0);

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SetPowerToughnessSourceEffect(
                new PermanentsOnBattlefieldCount(new FilterControlledCreaturePermanent()), Duration.EndOfGame)));
    }

    public VoiceOfResurgenceToken(final VoiceOfResurgenceToken token) {
        super(token);
    }

    public VoiceOfResurgenceToken copy() {
        return new VoiceOfResurgenceToken(this);
    }
}
