package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class GeistHonoredMonk extends CardImpl {

    public GeistHonoredMonk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        this.addAbility(VigilanceAbility.getInstance());

        // Geist-Honored Monk's power and toughness are each equal to the number of creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(CreaturesYouControlCount.instance))
                .addHint(CreaturesYouControlHint.instance));

        // When Geist-Honored Monk enters the battlefield, create two 1/1 white Spirit creature tokens with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SpiritWhiteToken(), 2)));
    }

    private GeistHonoredMonk(final GeistHonoredMonk card) {
        super(card);
    }

    @Override
    public GeistHonoredMonk copy() {
        return new GeistHonoredMonk(this);
    }
}
