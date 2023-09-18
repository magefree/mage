
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class FloodwaterDam extends CardImpl {

    public FloodwaterDam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {X}{X}{1}, {tap}: Tap X target lands.
        Effect effect = new TapTargetEffect();
        effect.setText("tap X target lands");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{X}{X}{1}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_LANDS));
        this.addAbility(ability);
    }

    private FloodwaterDam(final FloodwaterDam card) {
        super(card);
    }

    @Override
    public FloodwaterDam copy() {
        return new FloodwaterDam(this);
    }
}
