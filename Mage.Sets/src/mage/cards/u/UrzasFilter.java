
package mage.cards.u;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.MulticoloredPredicate;

/**
 *
 * @author LevelX2
 */
public final class UrzasFilter extends CardImpl {

    private static final FilterCard filter = new FilterCard("multicolored spells");

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public UrzasFilter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Multicolored spells cost up to {2} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionAllEffect(filter, 2, true)));
    }

    private UrzasFilter(final UrzasFilter card) {
        super(card);
    }

    @Override
    public UrzasFilter copy() {
        return new UrzasFilter(this);
    }
}
