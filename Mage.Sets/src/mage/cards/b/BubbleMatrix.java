
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;

/**
 *
 * @author LoneFox
 */
public final class BubbleMatrix extends CardImpl {

    public BubbleMatrix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Prevent all damage that would be dealt to creatures.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventAllDamageToAllEffect(Duration.WhileOnBattlefield, FILTER_PERMANENT_CREATURES)));
    }

    public BubbleMatrix(final BubbleMatrix card) {
        super(card);
    }

    @Override
    public BubbleMatrix copy() {
        return new BubbleMatrix(this);
    }
}
