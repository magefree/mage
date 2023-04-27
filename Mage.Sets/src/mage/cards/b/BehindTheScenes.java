
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.SkulkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;

/**
 *
 * @author fireshoes
 */
public final class BehindTheScenes extends CardImpl {

    public BehindTheScenes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // Creatures you control have skulk.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(new SkulkAbility(), Duration.WhileOnBattlefield, FILTER_PERMANENT_CREATURES)));

        // {4}{W}: Creatures you control get +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostControlledEffect(1, 1, Duration.EndOfTurn, FILTER_PERMANENT_CREATURES),
                new ManaCostsImpl<>("{4}{W}")));
    }

    private BehindTheScenes(final BehindTheScenes card) {
        super(card);
    }

    @Override
    public BehindTheScenes copy() {
        return new BehindTheScenes(this);
    }
}
