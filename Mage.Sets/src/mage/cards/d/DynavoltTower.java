
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author emerald000
 */
public final class DynavoltTower extends CardImpl {

    public DynavoltTower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // Whenever you cast an instant or sorcery spell, you get {E}{E}.
        this.addAbility(new SpellCastControllerTriggeredAbility(new GetEnergyCountersControllerEffect(2), new FilterInstantOrSorcerySpell(), false));

        // {T}, Pay {E}{E}{E}{E}{E}: Dynavolt Tower deals 3 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(3), new TapSourceCost());
        ability.addCost(new PayEnergyCost(5));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private DynavoltTower(final DynavoltTower card) {
        super(card);
    }

    @Override
    public DynavoltTower copy() {
        return new DynavoltTower(this);
    }
}
