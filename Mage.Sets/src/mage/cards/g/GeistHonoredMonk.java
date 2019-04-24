
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.SpiritWhiteToken;

/**
 * @author nantuko
 */
public final class GeistHonoredMonk extends CardImpl {

    public GeistHonoredMonk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        this.addAbility(VigilanceAbility.getInstance());

        // Geist-Honored Monk's power and toughness are each equal to the number of creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(new FilterControlledCreaturePermanent()), Duration.EndOfGame)));

        // When Geist-Honored Monk enters the battlefield, create two 1/1 white Spirit creature tokens with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SpiritWhiteToken("ISD"), 2)));
    }

    public GeistHonoredMonk(final GeistHonoredMonk card) {
        super(card);
    }

    @Override
    public GeistHonoredMonk copy() {
        return new GeistHonoredMonk(this);
    }
}
