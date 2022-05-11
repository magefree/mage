package mage.cards.d;

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
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class DynavoltTower extends CardImpl {

    public DynavoltTower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever you cast an instant or sorcery spell, you get {E}{E}.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new GetEnergyCountersControllerEffect(2),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));

        // {T}, Pay {E}{E}{E}{E}{E}: Dynavolt Tower deals 3 damage to any target.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(3), new TapSourceCost());
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
