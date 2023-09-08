
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class FateTransfer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("target creature to move all counters from");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("another target creature to move all counters to");

    public FateTransfer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U/B}");

        // Move all counters from target creature onto another target creature.
        this.getSpellAbility().addEffect(new FateTransferEffect());
        
        TargetCreaturePermanent fromTarget = new TargetCreaturePermanent(filter);
        fromTarget.setTargetTag(1);
        this.getSpellAbility().addTarget(fromTarget);
        
        TargetCreaturePermanent toTarget = new TargetCreaturePermanent(filter2);
        filter2.add(new AnotherTargetPredicate(2));
        toTarget.setTargetTag(2);
        this.getSpellAbility().addTarget(toTarget);

    }

    private FateTransfer(final FateTransfer card) {
        super(card);
    }

    @Override
    public FateTransfer copy() {
        return new FateTransfer(this);
    }
}

class FateTransferEffect extends OneShotEffect {

    public FateTransferEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "Move all counters from target creature onto another target creature";
    }

    private FateTransferEffect(final FateTransferEffect effect) {
        super(effect);
    }

    @Override
    public FateTransferEffect copy() {
        return new FateTransferEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creatureToMoveCountersFrom = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        Permanent creatureToMoveCountersTo = game.getPermanent(source.getTargets().get(1).getFirstTarget());

        if (creatureToMoveCountersFrom != null
                && creatureToMoveCountersTo != null) {
            Permanent copyCreature = creatureToMoveCountersFrom.copy();
            for (Counter counter : copyCreature.getCounters(game).values()) {
                creatureToMoveCountersFrom.removeCounters(counter, source, game);
                creatureToMoveCountersTo.addCounters(counter, source.getControllerId(), source, game);
            }
            return true;
        }
        return false;
    }
}
