package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RemoveUpToAmountCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EventidesShadow extends CardImpl {

    public EventidesShadow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Remove any number of counters from among permanents on the battlefield. You draw cards and lose life equal to the number of counters removed this way.
        this.getSpellAbility().addEffect(new EventidesShadowEffect());
    }

    private EventidesShadow(final EventidesShadow card) {
        super(card);
    }

    @Override
    public EventidesShadow copy() {
        return new EventidesShadow(this);
    }
}

class EventidesShadowEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("permanents");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    EventidesShadowEffect() {
        super(Outcome.Benefit);
        staticText = "remove any number of counters from among permanents on the battlefield. " +
                "You draw cards and lose life equal to the number of counters removed this way";
    }

    private EventidesShadowEffect(final EventidesShadowEffect effect) {
        super(effect);
    }

    @Override
    public EventidesShadowEffect copy() {
        return new EventidesShadowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
        target.withChooseHint("to remove counters from");
        player.choose(outcome, target, source, game);
        int total = 0;
        for (UUID targetId : target.getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            total += RemoveUpToAmountCountersEffect.doRemoval(Integer.MAX_VALUE, targetId, player, game, source);
        }
        if (total < 1) {
            return false;
        }
        game.processAction();
        player.drawCards(total, source, game);
        game.processAction();
        player.loseLife(total, game, source, false);
        return true;
    }
}
