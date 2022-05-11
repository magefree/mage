
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Styxo
 */
public final class Dismantle extends CardImpl {

    public Dismantle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Destroy target artifact. If that artifact had counters on it, put that many +1/+1 counters or charge counters on an artifact you control.
        this.getSpellAbility().addEffect(new DismantleEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());

    }

    private Dismantle(final Dismantle card) {
        super(card);
    }

    @Override
    public Dismantle copy() {
        return new Dismantle(this);
    }

}

class DismantleEffect extends OneShotEffect {

    public DismantleEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target artifact. If that artifact had counters on it, put that many +1/+1 counters or charge counters on an artifact you control";

    }

    public DismantleEffect(final DismantleEffect effect) {
        super(effect);
    }

    @Override
    public DismantleEffect copy() {
        return new DismantleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null) {
            if (permanent != null) {
                int counterCount = 0;
                counterCount = permanent.getCounters(game).values().stream().map((counter) -> counter.getCount()).reduce(counterCount, Integer::sum);
                permanent.destroy(source, game, false);
                if (counterCount > 0) {
                    Target target = new TargetControlledPermanent(1, 1, new FilterControlledArtifactPermanent("an artifact you control"), true);
                    if (target.canChoose(controller.getId(), source, game)) {
                        controller.chooseTarget(Outcome.Benefit, target, source, game);
                        Permanent artifact = game.getPermanent(target.getFirstTarget());
                        Counter counter;
                        if (controller.chooseUse(Outcome.BoostCreature, "Choose which kind of counters to add", null, "+1/+1 counters", "Charge counters", source, game)) {
                            counter = CounterType.P1P1.createInstance(counterCount);
                        } else {
                            counter = CounterType.CHARGE.createInstance(counterCount);
                        }
                        if (artifact != null) {
                            artifact.addCounters(counter, source.getControllerId(), source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

}
