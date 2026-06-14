package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.MeleeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class TitaniaProudPummeler extends CardImpl {

    public TitaniaProudPummeler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Melee
        this.addAbility(new MeleeAbility());

        // Other creatures you control have melee.
        this.addAbility(new SimpleStaticAbility(
            new GainAbilityControlledEffect(
                new MeleeAbility(),
                Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES,
                true
            )
        ));

    }

    private TitaniaProudPummeler(final TitaniaProudPummeler card) {
        super(card);
    }

    @Override
    public TitaniaProudPummeler copy() {
        return new TitaniaProudPummeler(this);
    }
}
