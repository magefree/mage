package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
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
public final class CanopyBaloth extends CardImpl {

    public CanopyBaloth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Landfall — Whenever a land enters the battlefield under your control, Canopy Baloth gets +2/+2 until end of turn.
        this.addAbility(new LandfallAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn)));
    }

    private CanopyBaloth(final CanopyBaloth card) {
        super(card);
    }

    @Override
    public CanopyBaloth copy() {
        return new CanopyBaloth(this);
    }
}
