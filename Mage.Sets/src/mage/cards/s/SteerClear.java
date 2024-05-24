package mage.cards.s;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.ConditionTrueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.common.TargetAttackingOrBlockingCreature;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class SteerClear extends CardImpl {

    public SteerClear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Steer Clear deals 2 damage to target attacking or blocking creature. Steer Clear deals 4 damage to that creature instead if you controlled a Mount as you cast this spell.
        this.getSpellAbility().addEffect(new SteerClearEffect());
        this.getSpellAbility().addWatcher(new SteerClearWatcher());
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
        this.getSpellAbility().addHint(new ConditionTrueHint(SteerClearCondition.instance));
    }

    private SteerClear(final SteerClear card) {
        super(card);
    }

    @Override
    public SteerClear copy() {
        return new SteerClear(this);
    }
}

enum SteerClearCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        SteerClearWatcher watcher = game.getState().getWatcher(SteerClearWatcher.class);
        return watcher != null
                && watcher.didControlMountOnCast(new MageObjectReference(source.getSourceObject(game), game));
    }

    @Override
    public String toString() {
        return "if you controlled a Mount as you cast this spell";
    }
}

class SteerClearEffect extends OneShotEffect {

    SteerClearEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 2 damage to target attacking or blocking creature. "
                + "{this} deals 4 damage to that creature instead if you controlled a Mount as you cast this spell.";
    }

    private SteerClearEffect(final SteerClearEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        SteerClearWatcher watcher = game.getState().getWatcher(SteerClearWatcher.class);
        if (watcher == null) {
            return false;
        }
        int amount = watcher.didControlMountOnCast(new MageObjectReference(source.getSourceObject(game), game))
                ? 4 : 2;
        return new DamageTargetEffect(amount)
                .setTargetPointer(getTargetPointer().copy())
                .apply(game, source);
    }

    @Override
    public SteerClearEffect copy() {
        return new SteerClearEffect(this);
    }

}

class SteerClearWatcher extends Watcher {

    private final Set<MageObjectReference> spellsCastWithMountControlled = new HashSet<>();

    private final FilterPermanent filter = new FilterPermanent(SubType.MOUNT, "");

    SteerClearWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell == null) {
            return;
        }
        if (0 == game.getBattlefield().countAll(filter, spell.getControllerId(), game)) {
            return;
        }
        spellsCastWithMountControlled.add(new MageObjectReference(spell, game));
    }

    @Override
    public void reset() {
        super.reset();
        spellsCastWithMountControlled.clear();
    }

    public boolean didControlMountOnCast(MageObjectReference mor) {
        return spellsCastWithMountControlled.contains(mor);
    }
}
