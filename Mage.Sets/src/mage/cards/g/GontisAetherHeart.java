
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author fireshoes
 */
public final class GontisAetherHeart extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("{this} or another artifact");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public GontisAetherHeart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        addSuperType(SuperType.LEGENDARY);

        // Whenever Gonti's Aether Heart or another artifact enters the battlefield under your control, you get {E}{E} <i>(two energy counters).
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new GetEnergyCountersControllerEffect(2), filter, false));

        // Pay {E}{E}{E}{E}{E}{E}{E}{E}, Exile Gonti's Aether Heart: Take an extra turn after this one.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddExtraTurnControllerEffect(), new PayEnergyCost(8));
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    public GontisAetherHeart(final GontisAetherHeart card) {
        super(card);
    }

    @Override
    public GontisAetherHeart copy() {
        return new GontisAetherHeart(this);
    }
}
