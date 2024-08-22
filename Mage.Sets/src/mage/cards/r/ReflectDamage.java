package mage.cards.r;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.TargetPlayer;
import mage.target.TargetSource;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class ReflectDamage extends CardImpl {

    public ReflectDamage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}{W}");

        // The next time a source of your choice would deal damage this turn, that damage is dealt to that source's controller instead.
        this.getSpellAbility().addEffect(new ReflectDamageEffect());
    }

    private ReflectDamage(final ReflectDamage card) {
        super(card);
    }

    @Override
    public ReflectDamage copy() {
        return new ReflectDamage(this);
    }
}

class ReflectDamageEffect extends RedirectionEffect {

    private final TargetSource damageSource;

    ReflectDamageEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, UsageType.ONE_USAGE_ABSOLUTE);
        staticText = "The next time a source of your choice would deal damage this turn, that damage is dealt to that source's controller instead";
        this.damageSource = new TargetSource();
    }

    private ReflectDamageEffect(final ReflectDamageEffect effect) {
        super(effect);
        this.damageSource = effect.damageSource.copy();
    }

    @Override
    public ReflectDamageEffect copy() {
        return new ReflectDamageEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        this.damageSource.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), source, game);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // check source, can be spell or other stack object, must find controller
        MageObject object = game.getObject(event.getSourceId());
        if (object instanceof Spell && ((Spell) object).getSourceId().equals(damageSource.getFirstTarget())) {
            TargetPlayer target = new TargetPlayer();
            target.add(((Spell) object).getControllerId(), game);
            this.redirectTarget = target;
            return true;
        }
        if (object instanceof Controllable && object.getId().equals(damageSource.getFirstTarget())) {
            TargetPlayer target = new TargetPlayer();
            target.add(((Controllable) object).getControllerId(), game);
            this.redirectTarget = target;
            return true;
        }
        return false;
    }

}
