
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class UrgeToFeed extends CardImpl {


    public UrgeToFeed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}{B}");

        // Target creature gets -3/-3 until end of turn. You may tap any number of untapped Vampire creatures you control. If you do, put a +1/+1 counter on each of those Vampires.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-3, -3, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new UrgeToFeedEffect());
    }

    private UrgeToFeed(final UrgeToFeed card) {
        super(card);
    }

    @Override
    public UrgeToFeed copy() {
        return new UrgeToFeed(this);
    }
}

class UrgeToFeedEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("untapped Vampire creatures you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.VAMPIRE.getPredicate());
    }

    public UrgeToFeedEffect() {
        super(Outcome.BoostCreature);
        staticText = "You may tap any number of untapped Vampire creatures you control. If you do, put a +1/+1 counter on each of those Vampires";
    }

    public UrgeToFeedEffect(UrgeToFeedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TargetCreaturePermanent target = new TargetCreaturePermanent(0, Integer.MAX_VALUE, filter, true);
        if (target.canChoose(source.getControllerId(), source, game) && target.choose(Outcome.Tap, source.getControllerId(), source.getSourceId(), source, game)) {
            for (UUID vampireId : target.getTargets()) {
                Permanent vampire = game.getPermanent(vampireId);
                if (vampire != null) {
                    vampire.tap(source, game);
                    vampire.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
                }
            }
        }
        return true;
    }

    @Override
    public UrgeToFeedEffect copy() {
        return new UrgeToFeedEffect(this);
    }
}
