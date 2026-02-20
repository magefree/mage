package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.common.AllianceAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class EastWindAvatar extends CardImpl {

    public EastWindAvatar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Alliance -- Whenever another creature you control enters, this creature gets +1/+0 until end of turn.
        this.addAbility(new AllianceAbility(new BoostSourceEffect(1, 0, Duration.EndOfTurn)));
    }

    private EastWindAvatar(final EastWindAvatar card) {
        super(card);
    }

    @Override
    public EastWindAvatar copy() {
        return new EastWindAvatar(this);
    }
}
