
package mage.cards.i;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreatureInPlay;

/**
 *
 * @author TheElk801
 */
public final class InnerSanctum extends CardImpl {

    public InnerSanctum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");

        // Cumulative upkeep-Pay 2 life.
        this.addAbility(new CumulativeUpkeepAbility(new PayLifeCost(2)));

        // Prevent all damage that would be dealt to creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new PreventAllDamageToAllEffect(Duration.WhileOnBattlefield, new FilterControlledCreatureInPlay("creatures you control"))
        ));
    }

    public InnerSanctum(final InnerSanctum card) {
        super(card);
    }

    @Override
    public InnerSanctum copy() {
        return new InnerSanctum(this);
    }
}
