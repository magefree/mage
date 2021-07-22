package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeartlessAct extends CardImpl {

    private static final FilterCreaturePermanent filterWithoutCounters
            = new FilterCreaturePermanent("creature with no counters on it");

    static {
        filterWithoutCounters.add(Predicates.not(CounterAnyPredicate.instance));
    }

    public HeartlessAct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Choose one —
        // • Destroy target creature with no counters on it.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filterWithoutCounters));

        // • Remove up to three counters from target creature.
        Mode mode = new Mode(new HeartlessActEffect());
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
    }

    private HeartlessAct(final HeartlessAct card) {
        super(card);
    }

    @Override
    public HeartlessAct copy() {
        return new HeartlessAct(this);
    }
}

class HeartlessActEffect extends OneShotEffect {

    HeartlessActEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "Remove up to three counters from target creature";
    }

    private HeartlessActEffect(final HeartlessActEffect effect) {
        super(effect);
    }

    @Override
    public HeartlessActEffect copy() {
        return new HeartlessActEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            int toRemove = 3;
            int removed = 0;
            String[] counterNames = permanent.getCounters(game).keySet().toArray(new String[0]);
            for (String counterName : counterNames) {
                if (controller.chooseUse(Outcome.Neutral, "Remove " + counterName + " counters?", source, game)) {
                    if (permanent.getCounters(game).get(counterName).getCount() == 1 || (toRemove - removed == 1)) {
                        permanent.removeCounters(counterName, 1, source, game);
                        removed++;
                    } else {
                        int amount = controller.getAmount(1, Math.min(permanent.getCounters(game).get(counterName).getCount(), toRemove - removed), "How many?", game);
                        if (amount > 0) {
                            removed += amount;
                            permanent.removeCounters(counterName, amount, source, game);
                        }
                    }
                }
                if (removed >= toRemove) {
                    break;
                }
            }
            game.addEffect(new BoostSourceEffect(removed, 0, Duration.EndOfTurn), source);
            return true;
        }
        return true;
    }
}