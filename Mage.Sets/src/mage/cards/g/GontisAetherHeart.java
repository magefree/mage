
package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class GontisAetherHeart extends CardImpl {

    public GontisAetherHeart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        this.supertype.add(SuperType.LEGENDARY);

        // Whenever Gonti's Aether Heart or another artifact enters the battlefield under your control, you get {E}{E} <i>(two energy counters).
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new GetEnergyCountersControllerEffect(2),
                StaticFilters.FILTER_PERMANENT_ARTIFACT, false, true
        ));

        // Pay {E}{E}{E}{E}{E}{E}{E}{E}, Exile Gonti's Aether Heart: Take an extra turn after this one.
        Ability ability = new SimpleActivatedAbility(new AddExtraTurnControllerEffect(), new PayEnergyCost(8));
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private GontisAetherHeart(final GontisAetherHeart card) {
        super(card);
    }

    @Override
    public GontisAetherHeart copy() {
        return new GontisAetherHeart(this);
    }
}
