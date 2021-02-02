package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class CivicStalwart extends CardImpl {

    public CivicStalwart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Civic Stalwart enters the battlefield, creatures you control get +1/+1 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new BoostControlledEffect(1, 1, Duration.EndOfTurn, new FilterCreaturePermanent("creatures"))));
    }

    private CivicStalwart(final CivicStalwart card) {
        super(card);
    }

    @Override
    public CivicStalwart copy() {
        return new CivicStalwart(this);
    }
}
