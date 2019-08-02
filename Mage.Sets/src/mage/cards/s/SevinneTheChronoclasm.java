package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SevinneTheChronoclasm extends CardImpl {

    public SevinneTheChronoclasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Prevent all damage that would be dealt to Sevinne, the Chronoclasm.
        this.addAbility(new SimpleStaticAbility(new PreventAllDamageToSourceEffect(Duration.WhileOnBattlefield)));

        // Whenever you cast your first instant or sorcery spell from your graveyard each turn, copy that spell. You may choose new targets for the copy.
        this.addAbility(new SevinneTheChronoclasmTriggeredAbility(), new SevinneTheChronoclasmWatcher());
    }

    private SevinneTheChronoclasm(final SevinneTheChronoclasm card) {
        super(card);
    }

    @Override
    public SevinneTheChronoclasm copy() {
        return new SevinneTheChronoclasm(this);
    }
}

class SevinneTheChronoclasmTriggeredAbility extends SpellCastControllerTriggeredAbility {

    SevinneTheChronoclasmTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY, false, true);
    }

    private SevinneTheChronoclasmTriggeredAbility(final SevinneTheChronoclasmTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game) || event.getZone() != Zone.GRAVEYARD) {
            return false;
        }
        SevinneTheChronoclasmWatcher watcher = game.getState().getWatcher(SevinneTheChronoclasmWatcher.class);
        if (watcher == null || !watcher.checkFirstSpellThisTurn(this.getControllerId(), event.getTargetId())) {
            return false;
        }
        this.getEffects().clear();
        Effect effect = new CopyTargetSpellEffect(true);
        effect.setTargetPointer(new FixedTarget(event.getTargetId(), game));
        this.addEffect(effect);
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you cast your first instant or sorcery spell from your graveyard each turn, " +
                "copy that spell. You may choose new targets for the copy.";
    }

    @Override
    public SevinneTheChronoclasmTriggeredAbility copy() {
        return new SevinneTheChronoclasmTriggeredAbility(this);
    }
}

class SevinneTheChronoclasmWatcher extends Watcher {

    private final Map<UUID, UUID> firstSpellThisTurn = new HashMap<>();

    SevinneTheChronoclasmWatcher() {
        super(WatcherScope.GAME);
    }

    private SevinneTheChronoclasmWatcher(final SevinneTheChronoclasmWatcher watcher) {
        super(watcher);
        this.firstSpellThisTurn.putAll(watcher.firstSpellThisTurn);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST
                || event.getZone() != Zone.GRAVEYARD) {
            return;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && spell.isInstantOrSorcery()) {
            firstSpellThisTurn.putIfAbsent(event.getPlayerId(), spell.getId());
        }
    }

    @Override
    public void reset() {
        firstSpellThisTurn.clear();
    }

    boolean checkFirstSpellThisTurn(UUID playerId, UUID targetId) {
        return targetId != null && targetId.equals(firstSpellThisTurn.getOrDefault(playerId, null));
    }

    @Override
    public Watcher copy() {
        return new SevinneTheChronoclasmWatcher(this);
    }
}
