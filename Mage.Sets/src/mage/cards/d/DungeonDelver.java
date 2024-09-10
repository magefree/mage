package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DungeonDelver extends CardImpl {

    public DungeonDelver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have "Room abilities of dungeons you own trigger an additional time."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                new SimpleStaticAbility(new DungeonDelverEffect()),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        ).withForceQuotes()));
    }

    private DungeonDelver(final DungeonDelver card) {
        super(card);
    }

    @Override
    public DungeonDelver copy() {
        return new DungeonDelver(this);
    }
}

class DungeonDelverEffect extends ReplacementEffectImpl {

    DungeonDelverEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "room abilities of dungeons you own trigger an additional time";
    }

    private DungeonDelverEffect(final DungeonDelverEffect effect) {
        super(effect);
    }

    @Override
    public DungeonDelverEffect copy() {
        return new DungeonDelverEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        GameEvent gameEvent = ((NumberOfTriggersEvent) event).getSourceEvent();
        return gameEvent != null
                && gameEvent.getType() == GameEvent.EventType.ROOM_ENTERED
                && source.isControlledBy(gameEvent.getPlayerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}
