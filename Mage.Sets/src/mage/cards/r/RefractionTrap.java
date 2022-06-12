package mage.cards.r;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSource;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.SpellsCastWatcher;

import java.util.List;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class RefractionTrap extends CardImpl {

    public RefractionTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");
        this.subtype.add(SubType.TRAP);

        // If an opponent cast a red instant or sorcery spell this turn, you may pay {W} rather than pay Refraction Trap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl<>("{W}"), RefractionTrapCondition.instance), new SpellsCastWatcher());

        // Prevent the next 3 damage that a source of your choice would deal to you and/or permanents you control this turn. If damage is prevented this way, Refraction Trap deals that much damage to any target.
        this.getSpellAbility().addEffect(new RefractionTrapPreventDamageEffect(Duration.EndOfTurn, 3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private RefractionTrap(final RefractionTrap card) {
        super(card);
    }

    @Override
    public RefractionTrap copy() {
        return new RefractionTrap(this);
    }
}

enum RefractionTrapCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher != null) {
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                List<Spell> spells = watcher.getSpellsCastThisTurn(opponentId);
                if (spells != null) {
                    for (Spell spell : spells) {
                        if ((spell.isSorcery(game) || spell.isInstant(game))
                                && spell.getColor(game).isRed()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "If an opponent cast a red instant or sorcery spell this turn";
    }
}

class RefractionTrapPreventDamageEffect extends PreventionEffectImpl {

    private final TargetSource target;
    private final int amount;

    public RefractionTrapPreventDamageEffect(Duration duration, int amount) {
        super(duration, amount, false, false);
        this.amount = amount;
        this.target = new TargetSource();
        staticText = "The next " + amount + " damage that a source of your choice would deal to you and/or permanents you control this turn. If damage is prevented this way, {this} deals that much damage to any target";
    }

    public RefractionTrapPreventDamageEffect(final RefractionTrapPreventDamageEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.target = effect.target.copy();
    }

    @Override
    public RefractionTrapPreventDamageEffect copy() {
        return new RefractionTrapPreventDamageEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        this.target.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), source, game);
        super.init(source, game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionData = preventDamageAction(event, source, game);
        this.used = true;
        this.discard(); // only one use
        if (preventionData.getPreventedDamage() > 0) {
            UUID damageTarget = getTargetPointer().getFirst(game, source);
            Permanent permanent = game.getPermanent(damageTarget);
            if (permanent != null) {
                game.informPlayers("Dealing " + preventionData.getPreventedDamage() + " to " + permanent.getLogName());
                permanent.damage(preventionData.getPreventedDamage(), source.getSourceId(), source, game, false, true);
            }
            Player player = game.getPlayer(damageTarget);
            if (player != null) {
                game.informPlayers("Dealing " + preventionData.getPreventedDamage() + " to " + player.getLogName());
                player.damage(preventionData.getPreventedDamage(), source.getSourceId(), source, game);
            }
        }

        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game)) {
            // check source
            MageObject object = game.getObject(event.getSourceId());
            if (object == null) {
                game.informPlayers("Couldn't find source of damage");
                return false;
            }

            // check damage source
            if (!object.getId().equals(target.getFirstTarget())
                    && !((object instanceof StackObject) && ((StackObject) object).getSourceId().equals(target.getFirstTarget()))) {
                return false;
            }

            // check target
            //   check permanent first
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null) {
                if (permanent.isControlledBy(source.getControllerId())) {
                    // it's your permanent
                    return true;
                }
            }
            //   check player
            Player player = game.getPlayer(event.getTargetId());
            if (player != null) {
                // it is you
                return player.getId().equals(source.getControllerId());
            }
        }
        return false;
    }
}
