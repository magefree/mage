package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.SquadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UltramarinesHonourGuard extends CardImpl {

    public UltramarinesHonourGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.ASTARTES);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Squad {2}
        this.addAbility(new SquadAbility());

        // Other creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, true
        )));
    }

    private UltramarinesHonourGuard(final UltramarinesHonourGuard card) {
        super(card);
    }

    @Override
    public UltramarinesHonourGuard copy() {
        return new UltramarinesHonourGuard(this);
    }
}
