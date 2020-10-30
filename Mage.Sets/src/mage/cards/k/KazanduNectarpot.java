package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KazanduNectarpot extends CardImpl {

    public KazanduNectarpot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Landfall — Whenever a land enters the battlefield under your control, you gain 1 life.
        this.addAbility(new LandfallAbility(new GainLifeEffect(1)));
    }

    private KazanduNectarpot(final KazanduNectarpot card) {
        super(card);
    }

    @Override
    public KazanduNectarpot copy() {
        return new KazanduNectarpot(this);
    }
}
