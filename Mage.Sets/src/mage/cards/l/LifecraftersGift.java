
package mage.cards.l;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class LifecraftersGift extends CardImpl {

    public LifecraftersGift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        // Put a +1/+1 counter on target creature, then put a +1/+1 counter on each creature you control with a +1/+1 counter on it.
        getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(1)));
        getSpellAbility().addTarget(new TargetCreaturePermanent());
        getSpellAbility().addEffect(new LifecraftersGiftEffect());
    }

    private LifecraftersGift(final LifecraftersGift card) {
        super(card);
    }

    @Override
    public LifecraftersGift copy() {
        return new LifecraftersGift(this);
    }
}

class LifecraftersGiftEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(CounterType.P1P1.getPredicate());
    }

    public LifecraftersGiftEffect() {
        super(Outcome.Benefit);
        this.staticText = ", then put a +1/+1 counter on each creature you control with a +1/+1 counter on it";
    }

    public LifecraftersGiftEffect(final LifecraftersGiftEffect effect) {
        super(effect);
    }

    @Override
    public LifecraftersGiftEffect copy() {
        return new LifecraftersGiftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            for(Permanent permanent: game.getState().getBattlefield().getAllActivePermanents(filter , controller.getId(), game)) {
                permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
                game.informPlayers(sourceObject.getName() + ": Put a +1/+1 counter on " + permanent.getLogName());
            }
        }
        return true;
    }
}
