package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AggressiveMammoth extends CardImpl {

    public AggressiveMammoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}{G}");

        this.subtype.add(SubType.ELEPHANT);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Other creatures you control have trample.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(
                        TrampleAbility.getInstance(),
                        Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_PERMANENT_CREATURES,
                        true
                )
        ));
    }

    public AggressiveMammoth(final AggressiveMammoth card) {
        super(card);
    }

    @Override
    public AggressiveMammoth copy() {
        return new AggressiveMammoth(this);
    }
}
