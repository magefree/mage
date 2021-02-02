package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author TheElk801
 */
public final class EarthOriginYak extends CardImpl {

    public EarthOriginYak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.OX);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Earth-Origin Yak enters the battlefield, creatures you control get +1/+1 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BoostControlledEffect(1, 1, Duration.EndOfTurn)));
    }

    private EarthOriginYak(final EarthOriginYak card) {
        super(card);
    }

    @Override
    public EarthOriginYak copy() {
        return new EarthOriginYak(this);
    }
}
