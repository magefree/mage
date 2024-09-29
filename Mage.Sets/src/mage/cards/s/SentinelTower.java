package mage.cards.s;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetAnyTarget;
import mage.watchers.Watcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SentinelTower extends CardImpl {

    public SentinelTower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Whenever an instant or sorcery spell is cast during your turn, Sentinel Tower deals damage to any target equal to 1 plus the number of instant and sorcery spells cast before that spell this turn.
        this.addAbility(new SentinelTowerTriggeredAbility(), new SentinelTowerWatcher());
    }

    private SentinelTower(final SentinelTower card) {
        super(card);
    }

    @Override
    public SentinelTower copy() {
        return new SentinelTower(this);
    }
}

class SentinelTowerTriggeredAbility extends SpellCastAllTriggeredAbility {

    private String damageInfo;

    SentinelTowerTriggeredAbility() {
        super(new DamageTargetEffect(0), StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false);
        this.addTarget(new TargetAnyTarget());
        this.addHint(new ValueHint("There were cast instant and sorcery this turn", SentinelTowerSpellsCastValue.instance));
        this.damageInfo = null;
    }

    private SentinelTowerTriggeredAbility(final SentinelTowerTriggeredAbility effect) {
        super(effect);
        this.damageInfo = effect.damageInfo;
    }

    @Override
    public SentinelTowerTriggeredAbility copy() {
        return new SentinelTowerTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.isActivePlayer(getControllerId())
                && super.checkTrigger(event, game)) {
            SentinelTowerWatcher watcher = game.getState().getWatcher(SentinelTowerWatcher.class);
            if (watcher == null) {
                return false;
            }
            List<MageObjectReference> spellsCast = watcher.getSpellsThisTurn();
            MageObject object = game.getObject(event.getTargetId());
            if (object == null || spellsCast == null) {
                return false;
            }
            int damageToDeal = 0;
            for (MageObjectReference mor : spellsCast) {
                damageToDeal++;
                if (mor.refersTo(object, game)) {
                    break;
                }
            }
            damageInfo = " (<b>" + damageToDeal + " damage</b>)";
            for (Effect effect : this.getEffects()) {
                if (effect instanceof DamageTargetEffect) {
                    ((DamageTargetEffect) effect).setAmount(StaticValue.get(damageToDeal));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an instant or sorcery spell is cast during your turn, "
                + "{this} deals damage to any target equal to 1 "
                + "plus the number of instant and sorcery spells cast before that spell this turn."
                + (damageInfo != null ? damageInfo : "");
    }
}

class SentinelTowerWatcher extends Watcher {

    private final List<MageObjectReference> spellsThisTurn;

    SentinelTowerWatcher() {
        super( WatcherScope.GAME);
        this.spellsThisTurn = new ArrayList<>();
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            MageObject object = game.getObject(event.getTargetId());
            if (object != null && object.isInstantOrSorcery(game)) {
                spellsThisTurn.add(new MageObjectReference(object, game));
            }
        }
    }

    @Override
    public void reset() {
        spellsThisTurn.clear();
    }

    public List<MageObjectReference> getSpellsThisTurn() {
        return spellsThisTurn;
    }
}

enum SentinelTowerSpellsCastValue implements DynamicValue {

    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        SentinelTowerWatcher watcher = game.getState().getWatcher(SentinelTowerWatcher.class);
        if (watcher == null) {
            return 0;
        }
        List<MageObjectReference> spellsCast = watcher.getSpellsThisTurn();
        if (spellsCast == null) {
            return 0;
        }
        return spellsCast.size();
    }

    @Override
    public SentinelTowerSpellsCastValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "There was an instant or sorcery spell in this turn";
    }
}