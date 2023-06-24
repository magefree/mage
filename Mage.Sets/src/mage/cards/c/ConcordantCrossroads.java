
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class ConcordantCrossroads extends CardImpl {

    public ConcordantCrossroads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}");
        this.supertype.add(SuperType.WORLD);

        // All creatures have haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES)));
    }

    private ConcordantCrossroads(final ConcordantCrossroads card) {
        super(card);
    }

    @Override
    public ConcordantCrossroads copy() {
        return new ConcordantCrossroads(this);
    }
}
