
package mage.cards.i;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;
import mage.filter.StaticFilters;

/**
 *
 * @author Plopman
 */
public final class IntruderAlarm extends CardImpl {

    public IntruderAlarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Creatures don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, FILTER_PERMANENT_CREATURES)));
        // Whenever a creature enters the battlefield, untap all creatures.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new UntapAllEffect(FILTER_PERMANENT_CREATURES), StaticFilters.FILTER_PERMANENT_A_CREATURE));
    }

    private IntruderAlarm(final IntruderAlarm card) {
        super(card);
    }

    @Override
    public IntruderAlarm copy() {
        return new IntruderAlarm(this);
    }
}
