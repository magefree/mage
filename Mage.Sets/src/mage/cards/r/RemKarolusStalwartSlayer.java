package mage.cards.r;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class RemKarolusStalwartSlayer extends CardImpl {

    public RemKarolusStalwartSlayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // If a spell would deal damage to you or another permanent you control, prevent that damage.
        this.addAbility(new SimpleStaticAbility(new RemKarolusStalwartSlayerPreventionEffect()));

        // If a spell would deal damage to an opponent or a permanent an opponent controls, it deals that much damage plus 1 instead.
        this.addAbility(new SimpleStaticAbility(new RemKarolusStalwartSlayerReplacementEffect()));
    }

    private RemKarolusStalwartSlayer(final RemKarolusStalwartSlayer card) {
        super(card);
    }

    @Override
    public RemKarolusStalwartSlayer copy() {
        return new RemKarolusStalwartSlayer(this);
    }
}

class RemKarolusStalwartSlayerPreventionEffect extends PreventionEffectImpl {

    public RemKarolusStalwartSlayerPreventionEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false, false);
        staticText = "If a spell would deal damage to you or another permanent you control, prevent that damage";
    }

    private RemKarolusStalwartSlayerPreventionEffect(final RemKarolusStalwartSlayerPreventionEffect effect) {
        super(effect);
    }

    @Override
    public RemKarolusStalwartSlayerPreventionEffect copy() {
        return new RemKarolusStalwartSlayerPreventionEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        UUID targetId = event.getTargetId();
        if (targetId.equals(source.getSourceId())) {
            return false;
        }
        UUID controllerId = source.getControllerId();
        if (!targetId.equals(controllerId)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null || !permanent.isControlledBy(controllerId)) {
                return false;
            }
        }
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        if (stackObject == null) {
            stackObject = (StackObject) game.getLastKnownInformation(event.getSourceId(), Zone.STACK);
        }
        if (stackObject instanceof Spell) {
            return super.applies(event, source, game);
        }
        return false;
    }
}

class RemKarolusStalwartSlayerReplacementEffect extends ReplacementEffectImpl {

    public RemKarolusStalwartSlayerReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If a spell would deal damage to an opponent or a permanent an opponent controls, it deals that much damage plus 1 instead";
    }

    private RemKarolusStalwartSlayerReplacementEffect(final RemKarolusStalwartSlayerReplacementEffect effect) {
        super(effect);
    }

    @Override
    public RemKarolusStalwartSlayerReplacementEffect copy() {
        return new RemKarolusStalwartSlayerReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 1));
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch(event.getType()) {
            case DAMAGE_PERMANENT:
            case DAMAGE_PLAYER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        UUID targetId = event.getTargetId();
        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        if (!opponents.contains(targetId)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null || !opponents.contains(permanent.getControllerId())) {
                return false;
            }
        }
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        if (stackObject == null) {
            stackObject = (StackObject) game.getLastKnownInformation(event.getSourceId(), Zone.STACK);
        }
        return stackObject instanceof Spell;
    }
}
