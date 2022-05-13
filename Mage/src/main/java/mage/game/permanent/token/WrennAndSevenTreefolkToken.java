package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class WrennAndSevenTreefolkToken extends TokenImpl {

    public WrennAndSevenTreefolkToken() {
        super("Treefolk Token", "green Treefolk creature token with reach and \"This creature's power and toughness are each equal to the number of lands you control.\"");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.TREEFOLK);
        power = new MageInt(0);
        toughness = new MageInt(0);
        this.addAbility(ReachAbility.getInstance());
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(
                LandsYouControlCount.instance, Duration.EndOfGame
        ).setText("this creature's power and toughness are each equal to the number of lands you control")));

        availableImageSetCodes.addAll(Arrays.asList("MID"));
    }

    public WrennAndSevenTreefolkToken(final WrennAndSevenTreefolkToken token) {
        super(token);
    }

    public WrennAndSevenTreefolkToken copy() {
        return new WrennAndSevenTreefolkToken(this);
    }
}
