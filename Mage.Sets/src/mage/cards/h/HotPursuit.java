
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostOpponentsEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.BountyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class HotPursuit extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Each creature with a bounty counter on it");

    static {
        filter.add(CounterType.BOUNTY.getPredicate());
    }

    public HotPursuit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}{R}{G}");

        // Each creature your opponent's control with a bounty counter on it gets -1/-1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostOpponentsEffect(-1, -1, Duration.WhileOnBattlefield, filter)));

        // <i>Bounty</i> &mdash; Whenever a creature an opponent controls with a bounty counter on it dies, you may put a bounty counter on target creature an opponent controls.
        Ability ability = new BountyAbility(new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()), true);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    public HotPursuit(final HotPursuit card) {
        super(card);
    }

    @Override
    public HotPursuit copy() {
        return new HotPursuit(this);
    }
}
