
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class LockjawSnapper extends CardImpl {

    public LockjawSnapper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Wither
        this.addAbility(WitherAbility.getInstance());
        
        // When Lockjaw Snapper dies, put a -1/-1 counter on each creature with a -1/-1 counter on it.
        this.addAbility(new DiesSourceTriggeredAbility(new LockjawSnapperEffect()));
        
    }

    private LockjawSnapper(final LockjawSnapper card) {
        super(card);
    }

    @Override
    public LockjawSnapper copy() {
        return new LockjawSnapper(this);
    }
}

class LockjawSnapperEffect extends OneShotEffect {

    public LockjawSnapperEffect() {
        super(Outcome.Neutral);
        this.staticText = "put a -1/-1 counter on each creature with a -1/-1 counter on it";
    }

    private LockjawSnapperEffect(final LockjawSnapperEffect effect) {
        super(effect);
    }

    @Override
    public LockjawSnapperEffect copy() {
        return new LockjawSnapperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(CounterType.M1M1.getPredicate());
        if (game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game).isEmpty()) {
            return true;
        }
        for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            if (creature != null) {
                creature.addCounters(CounterType.M1M1.createInstance(), source.getControllerId(), source, game);
                applied = true;
            }
        }
        return applied;
    }
}