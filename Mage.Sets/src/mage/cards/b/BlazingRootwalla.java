package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author jmharmon
 */

public final class BlazingRootwalla extends CardImpl {

    public BlazingRootwalla(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {R}: Blazing Rootwalla gets +2/+0 until end of turn. Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(2, 0, Duration.EndOfTurn), new ManaCostsImpl("{R}")));

        // Madness{0}
        this.addAbility(new MadnessAbility(new ManaCostsImpl("{0}")));
    }

    private BlazingRootwalla(final BlazingRootwalla card) {
        super(card);
    }

    @Override
    public BlazingRootwalla copy() {
        return new BlazingRootwalla(this);
    }
}
