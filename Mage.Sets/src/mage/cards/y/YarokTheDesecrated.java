package mage.cards.y;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YarokTheDesecrated extends CardImpl {

    public YarokTheDesecrated(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // If a permanent entering the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new YarokTheDesecratedEffect()));
    }

    private YarokTheDesecrated(final YarokTheDesecrated card) {
        super(card);
    }

    @Override
    public YarokTheDesecrated copy() {
        return new YarokTheDesecrated(this);
    }
}

class YarokTheDesecratedEffect extends ReplacementEffectImpl {

    YarokTheDesecratedEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a permanent entering the battlefield causes a triggered ability " +
                "of a permanent you control to trigger, that ability triggers an additional time";
    }

    private YarokTheDesecratedEffect(final YarokTheDesecratedEffect effect) {
        super(effect);
    }

    @Override
    public YarokTheDesecratedEffect copy() {
        return new YarokTheDesecratedEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!(event instanceof NumberOfTriggersEvent)) {
            return false;
        }
        NumberOfTriggersEvent numberOfTriggersEvent = (NumberOfTriggersEvent) event;
        if (!source.isControlledBy(event.getPlayerId())) {
            return false;
        }
        GameEvent sourceEvent = numberOfTriggersEvent.getSourceEvent();
        // Only EtB triggers
        if (sourceEvent == null
                || sourceEvent.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                || !(sourceEvent instanceof EntersTheBattlefieldEvent)) {
            return false;
        }
        // Only for triggers of permanents
        return game.getPermanent(numberOfTriggersEvent.getSourceId()) != null;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}
