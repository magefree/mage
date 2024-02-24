package mage.cards.e;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class Excavation extends CardImpl {

    public Excavation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // {1}, Sacrifice a land: Draw a card. Any player may activate this ability.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{1}"));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_LAND));
        ability.setMayActivate(TargetController.ANY);
        this.addAbility(ability);
    }

    private Excavation(final Excavation card) {
        super(card);
    }

    @Override
    public Excavation copy() {
        return new Excavation(this);
    }
}
