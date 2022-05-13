package mage.cards.f;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.UnblockedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public final class Forcefield extends CardImpl {

    public Forcefield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {1}: The next time an unblocked creature of your choice would deal combat damage to you this turn, prevent all but 1 of that damage.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ForcefieldEffect(), new GenericManaCost(1)));
    }

    private Forcefield(final Forcefield card) {
        super(card);
    }

    @Override
    public Forcefield copy() {
        return new Forcefield(this);
    }
}

class ForcefieldEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("an unblocked creature");

    static {
        filter.add(UnblockedPredicate.instance);
    }

    ForcefieldEffect() {
        super(Outcome.PreventDamage);
        this.staticText = "The next time an unblocked creature of your choice would deal combat damage to you this turn, prevent all but 1 of that damage";
    }

    ForcefieldEffect(final ForcefieldEffect effect) {
        super(effect);
    }

    @Override
    public ForcefieldEffect copy() {
        return new ForcefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Target target = new TargetCreaturePermanent(1, 1, filter, true);
            if (controller.choose(Outcome.PreventDamage, target, source, game)) {
                Permanent creature = game.getPermanent(target.getFirstTarget());
                if (creature != null) {
                    game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " has chosen " + creature.getLogName());
                }
                ContinuousEffect effect = new ForcefieldPreventionEffect();
                effect.setTargetPointer(new FixedTarget(target.getFirstTarget(), game));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}

class ForcefieldPreventionEffect extends PreventionEffectImpl {

    ForcefieldPreventionEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, true, false);
        this.staticText = "Prevent all but 1 of that damage";
    }

    ForcefieldPreventionEffect(ForcefieldPreventionEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int damage = event.getAmount();
        if (damage > 0) {
            this.amountToPrevent = damage - 1;
            preventDamageAction(event, source, game);
            this.discard();
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game)
                && event.getSourceId().equals(this.getTargetPointer().getFirst(game, source));
    }

    @Override
    public ForcefieldPreventionEffect copy() {
        return new ForcefieldPreventionEffect(this);
    }
}
