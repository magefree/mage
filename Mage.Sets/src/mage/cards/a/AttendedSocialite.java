package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.AllianceAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AttendedSocialite extends CardImpl {

    public AttendedSocialite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Alliance — Whenever another creature enters the battlefield under your control, Attended Socialite gets +1/+1 until end of turn.
        this.addAbility(new AllianceAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn)));
    }

    private AttendedSocialite(final AttendedSocialite card) {
        super(card);
    }

    @Override
    public AttendedSocialite copy() {
        return new AttendedSocialite(this);
    }
}
