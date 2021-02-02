
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author Styxo
 */
public final class ConsulateTurret extends CardImpl {

    public ConsulateTurret(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: You get {E}.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GetEnergyCountersControllerEffect(1), new TapSourceCost()));

        // {T}, Pay {E}{E}{E}: Consulate Turret deals 2 damage to target player.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new TapSourceCost());
        ability.addCost(new PayEnergyCost(3));
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private ConsulateTurret(final ConsulateTurret card) {
        super(card);
    }

    @Override
    public ConsulateTurret copy() {
        return new ConsulateTurret(this);
    }
}
