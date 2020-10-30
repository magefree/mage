package mage.cards.k;

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
public final class KazanduMammoth extends CardImpl {

    public KazanduMammoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.subtype.add(SubType.ELEPHANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.k.KazanduValley.class;

        // Landfall — Whenever a land enters the battlefield under your control, Kazandu Mammoth gets +2/+2 until end of turn.
        this.addAbility(new LandfallAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn)));
    }

    private KazanduMammoth(final KazanduMammoth card) {
        super(card);
    }

    @Override
    public KazanduMammoth copy() {
        return new KazanduMammoth(this);
    }
}
