
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author fireshoes
 */
public final class EraOfInnovation extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an artifact or Artificer");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(),
                SubType.ARTIFICER.getPredicate()));
    }

    public EraOfInnovation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");

        // Whenever an artifact or Artificer enters the battlefield under you control, you may pay {1}. If you do, you get {E}{E}.
        Effect effect = new DoIfCostPaid(new GetEnergyCountersControllerEffect(2), new GenericManaCost(1));
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(effect, filter,
                "Whenever an artifact or Artificer enters the battlefield under you control, you may pay {1}. If you do, you get {E}{E}."));

        // {E}{E}{E}{E}{E}{E}, Sacrifice Era of Innovation: Draw three cards.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(3), new PayEnergyCost(6));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private EraOfInnovation(final EraOfInnovation card) {
        super(card);
    }

    @Override
    public EraOfInnovation copy() {
        return new EraOfInnovation(this);
    }
}
