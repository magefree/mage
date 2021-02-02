
package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantBeTargetedAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;

/**
 *
 * @author Quercitron
 */
public final class DenseFoliage extends CardImpl {

    public DenseFoliage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");


        // Creatures can't be the targets of spells.
        CantBeTargetedAllEffect cantTargetEffect = new CantBeTargetedAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES, new FilterSpell("spells"), Duration.WhileOnBattlefield);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, cantTargetEffect));
    }

    private DenseFoliage(final DenseFoliage card) {
        super(card);
    }

    @Override
    public DenseFoliage copy() {
        return new DenseFoliage(this);
    }
}
