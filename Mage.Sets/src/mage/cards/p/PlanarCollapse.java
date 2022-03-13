
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;

/**
 *
 * @author Plopman
 */
public final class PlanarCollapse extends CardImpl {

    public PlanarCollapse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // At the beginning of your upkeep, if there are four or more creatures on the battlefield, sacrifice Planar Collapse and destroy all creatures. They can't be regenerated.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SacrificeSourceEffect(), TargetController.YOU, false);
        ability.addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURE, true));
        PlanarCollapseCondition contition = new PlanarCollapseCondition();
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, contition, "At the beginning of your upkeep, if there are four or more creatures on the battlefield, sacrifice {this} and destroy all creatures. They can't be regenerated"));

    }

    private PlanarCollapse(final PlanarCollapse card) {
        super(card);
    }

    @Override
    public PlanarCollapse copy() {
        return new PlanarCollapse(this);
    }

    static class PlanarCollapseCondition implements mage.abilities.condition.Condition {

        @Override
        public boolean apply(Game game, Ability source) {
            return game.getBattlefield().count(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game) >= 4;
        }
    }
}
