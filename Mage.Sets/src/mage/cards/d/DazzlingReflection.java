package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class DazzlingReflection extends CardImpl {

    public DazzlingReflection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // You gain life equal to target creature's power. The next time that creature would deal damage this turn, prevent that damage.
        getSpellAbility().addEffect(new DazzlingReflectionEffect());
        getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private DazzlingReflection(final DazzlingReflection card) {
        super(card);
    }

    @Override
    public DazzlingReflection copy() {
        return new DazzlingReflection(this);
    }
}

class DazzlingReflectionEffect extends OneShotEffect {

    public DazzlingReflectionEffect() {
        super(Outcome.Benefit);
        this.staticText = "You gain life equal to target creature's power. The next time that creature would deal damage this turn, prevent that damage";
    }

    private DazzlingReflectionEffect(final DazzlingReflectionEffect effect) {
        super(effect);
    }

    @Override
    public DazzlingReflectionEffect copy() {
        return new DazzlingReflectionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetCreature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
            if (targetCreature != null) {
                controller.gainLife(targetCreature.getPower().getValue(), game, source);
                ContinuousEffect effect = new DazzlingReflectionPreventEffect();
                effect.setTargetPointer(new FixedTarget(targetCreature, game));
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }
}

class DazzlingReflectionPreventEffect extends PreventionEffectImpl {

    public DazzlingReflectionPreventEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false, false);
        staticText = "The next time that creature would deal damage this turn, prevent that damage";
    }

    private DazzlingReflectionPreventEffect(final DazzlingReflectionPreventEffect effect) {
        super(effect);
    }

    @Override
    public DazzlingReflectionPreventEffect copy() {
        return new DazzlingReflectionPreventEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        super.replaceEvent(event, source, game);
        discard();
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getSourceId().equals(getTargetPointer().getFirst(game, source))) {
                return true;
            }
        }
        return false;
    }

}
