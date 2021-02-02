
package mage.cards.o;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 * @author Loki
 */
public final class OvergrownEstate extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent("a land");

    public OvergrownEstate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{B}{G}");

        // Sacrifice a land: You gain 3 life.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(3), new SacrificeTargetCost(new TargetControlledPermanent(filter))));
    }

    private OvergrownEstate(final OvergrownEstate card) {
        super(card);
    }

    @Override
    public OvergrownEstate copy() {
        return new OvergrownEstate(this);
    }
}
