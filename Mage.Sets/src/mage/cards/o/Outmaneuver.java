package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Outmaneuver extends CardImpl {

    public Outmaneuver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}");

        // X target blocked creatures assign their combat damage this turn as though they weren't blocked.
        this.getSpellAbility().addEffect(new OutmaneuverEffect());
        this.getSpellAbility().setTargetAdjuster(OutmaneuverAdjuster.instance);
    }

    private Outmaneuver(final Outmaneuver card) {
        super(card);
    }

    @Override
    public Outmaneuver copy() {
        return new Outmaneuver(this);
    }
}

enum OutmaneuverAdjuster implements TargetAdjuster {
    instance;
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(BlockedPredicate.instance);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int numberOfTargets = ability.getManaCostsToPay().getX();
        ability.addTarget(new TargetCreaturePermanent(numberOfTargets, numberOfTargets, filter, false));
    }
}

class OutmaneuverEffect extends AsThoughEffectImpl {

    OutmaneuverEffect() {
        super(AsThoughEffectType.DAMAGE_NOT_BLOCKED, Duration.EndOfTurn, Outcome.Damage);
        this.staticText = "X target blocked creatures assign their combat damage this turn as though they weren't blocked.";
    }

    private OutmaneuverEffect(OutmaneuverEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return targetPointer.getTargets(game, source).contains(sourceId);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public OutmaneuverEffect copy() {
        return new OutmaneuverEffect(this);
    }
}
