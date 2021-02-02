
package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class FyndhornPollen extends CardImpl {

    private static FilterCreaturePermanent filter = new FilterCreaturePermanent("All creatures");

    public FyndhornPollen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl("{1}")));

        // All creatures get -1/-0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(-1, 0, Duration.WhileOnBattlefield, filter, false)));

        // {1}{G}: All creatures get -1/-0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostAllEffect(-1, 0, Duration.EndOfTurn, filter, false), new ManaCostsImpl("{1}{G}")));
    }

    private FyndhornPollen(final FyndhornPollen card) {
        super(card);
    }

    @Override
    public FyndhornPollen copy() {
        return new FyndhornPollen(this);
    }
}
