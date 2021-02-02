package mage.cards.b;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class BubbleMatrix extends CardImpl {

    public BubbleMatrix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Prevent all damage that would be dealt to creatures.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventAllDamageToAllEffect(Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES)));
    }

    private BubbleMatrix(final BubbleMatrix card) {
        super(card);
    }

    @Override
    public BubbleMatrix copy() {
        return new BubbleMatrix(this);
    }
}
