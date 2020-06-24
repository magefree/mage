package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WardedBattlements extends CardImpl {

    public WardedBattlements(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Attacking creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 0, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_ATTACKING_CREATURES
        )));
    }

    private WardedBattlements(final WardedBattlements card) {
        super(card);
    }

    @Override
    public WardedBattlements copy() {
        return new WardedBattlements(this);
    }
}
