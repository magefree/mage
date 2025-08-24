package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class FateTransfer extends CardImpl {

    public FateTransfer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U/B}");

        // Move all counters from target creature onto another target creature.
        this.getSpellAbility().addEffect(new FateTransferEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("to remove counters from").setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2).setTargetTag(2));
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

    FateTransferEffect() {
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
