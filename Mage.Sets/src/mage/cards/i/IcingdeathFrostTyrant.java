package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.IcingdeathFrostTongueToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IcingdeathFrostTyrant extends CardImpl {

    public IcingdeathFrostTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Icingdeath, Frost Tyrant dies, create Icingdeath, Frost Tongue, a legendary white Equipment artifact token with "Equipped creature gets +2/+0", "Whenever equipped creature attacks, tap target creature defending player controls," and equip {2}.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new IcingdeathFrostTongueToken())));
    }

    private IcingdeathFrostTyrant(final IcingdeathFrostTyrant card) {
        super(card);
    }

    @Override
    public IcingdeathFrostTyrant copy() {
        return new IcingdeathFrostTyrant(this);
    }
}
