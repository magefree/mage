package mage.cards.r;

import java.util.UUID;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.Mana;
import mage.abilities.common.SimpleStaticAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author muz
 */
public final class RingOfUnity extends CardImpl {

    public RingOfUnity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{7}");

        // Artifacts you control have "{T}: Add {C}{C}."
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(2), new TapSourceCost());
        this.addAbility(new SimpleStaticAbility(
            new GainAbilityControlledEffect(
                ability, Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACTS
            )
        ));
    }

    private RingOfUnity(final RingOfUnity card) {
        super(card);
    }

    @Override
    public RingOfUnity copy() {
        return new RingOfUnity(this);
    }
}
