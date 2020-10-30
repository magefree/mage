package mage.cards.a;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class AkoumHellhound extends CardImpl {

    public AkoumHellhound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Landfall — Whenever a land enters the battlefield under your control, Akoum Hellhound gets +2/+2 until end of turn.
        this.addAbility(new LandfallAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn)));
    }

    private AkoumHellhound(final AkoumHellhound card) {
        super(card);
    }

    @Override
    public AkoumHellhound copy() {
        return new AkoumHellhound(this);
    }
}
