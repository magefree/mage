package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.BatchEvent;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar, Susucr
 */
public final class FelixFiveBoots extends CardImpl {

    public FelixFiveBoots(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OOZE, SubType.ROGUE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Ward {2}
        this.addAbility(new WardAbility(new GenericManaCost(2), false));

        // If a creature you control dealing combat damage to a player causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new FelixFiveBootsEffect()));
    }

    private FelixFiveBoots(final FelixFiveBoots card) {
        super(card);
    }

    @Override
    public FelixFiveBoots copy() {
        return new FelixFiveBoots(this);
    }
}

class FelixFiveBootsEffect extends ReplacementEffectImpl {

    FelixFiveBootsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a creature you control dealing combat damage to a player causes a triggered ability " +
                "of a permanent you control to trigger, that ability triggers an additional time";
    }

    private FelixFiveBootsEffect(final FelixFiveBootsEffect effect) {
        super(effect);
    }

    @Override
    public FelixFiveBootsEffect copy() {
        return new FelixFiveBootsEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // Permanent whose ability is being triggered (and will be retriggered)
        Permanent triggeringPermanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (triggeringPermanent == null || !triggeringPermanent.isControlledBy(source.getControllerId())) {
            return false;
        }
        GameEvent sourceEvent = ((NumberOfTriggersEvent) event).getSourceEvent();
        // check all damage events and damage batch events
        if (sourceEvent instanceof DamagedEvent) {
            return checkDamagedEvent((DamagedEvent) sourceEvent, source.getControllerId(), game);
        } else if (sourceEvent instanceof BatchEvent) {
            TriggeredAbility sourceTrigger = ((NumberOfTriggersEvent) event).getSourceTrigger();
            for (Object o : sourceTrigger instanceof BatchTriggeredAbility
                    ? ((BatchTriggeredAbility<?>) sourceTrigger).getFilteredEvents((BatchEvent) sourceEvent, game)
                    : ((BatchEvent<?>) sourceEvent).getEvents()) {
                if (o instanceof DamagedEvent && checkDamagedEvent((DamagedEvent) o, source.getControllerId(), game)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Checks that a given DamagedEvent matches with
    // "If a creature you control dealing combat damage to a player"
    private static boolean checkDamagedEvent(DamagedEvent event, UUID controllerId, Game game) {
        if (!event.isCombatDamage() || game.getPlayer(event.getTargetId()) == null) {
            return false;
        }
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        return sourcePermanent != null
                && sourcePermanent.isCreature(game)
                && sourcePermanent.isControlledBy(controllerId);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}
