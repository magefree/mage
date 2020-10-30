package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CanyonJerboa extends CardImpl {

    public CanyonJerboa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.MOUSE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Landfall — Whenever a land enters the battlefield under your control, creatures you control get +1/+1 until end of turn.
        this.addAbility(new LandfallAbility(new BoostControlledEffect(1, 1, Duration.EndOfTurn)));
    }

    private CanyonJerboa(final CanyonJerboa card) {
        super(card);
    }

    @Override
    public CanyonJerboa copy() {
        return new CanyonJerboa(this);
    }
}
