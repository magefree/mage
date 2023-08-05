
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class PendrellMists extends CardImpl {

    public PendrellMists(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        // All creatures have "At the beginning of your upkeep, sacrifice this creature unless you pay {1}."
        Ability gainedAbility = new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new GenericManaCost(1)), TargetController.YOU, false);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(gainedAbility, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES,
                "All creatures have \"At the beginning of your upkeep, sacrifice this creature unless you pay {1}")));
    }

    private PendrellMists(final PendrellMists card) {
        super(card);
    }

    @Override
    public PendrellMists copy() {
        return new PendrellMists(this);
    }
}
