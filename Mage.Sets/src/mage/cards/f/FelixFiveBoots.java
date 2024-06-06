package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
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
import mage.players.Player;

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
        NumberOfTriggersEvent numberOfTriggersEvent = (NumberOfTriggersEvent) event;
        Permanent sourcePermanent = game.getPermanent(numberOfTriggersEvent.getSourceId());
        if (sourcePermanent == null || !sourcePermanent.isControlledBy(source.getControllerId())) {
            return false;
        }

        GameEvent sourceEvent = numberOfTriggersEvent.getSourceEvent();
        if (sourceEvent == null) {
            return false;
        }

        if (sourceEvent instanceof DamagedEvent) {
            return checkDamagedEvent((DamagedEvent) sourceEvent, source.getControllerId(), game);
        } else if (sourceEvent instanceof BatchEvent) {
            for (Object singleEventAsObject : ((BatchEvent) sourceEvent).getEvents()) {
                if (singleEventAsObject instanceof DamagedEvent
                        && checkDamagedEvent((DamagedEvent) singleEventAsObject, source.getControllerId(), game)
                ) {
                    // For batch events, if one of the event inside the condition match the condition,
                    // the effect applies to the whole batch events.
                    return true;
                }
            }
        }

        return false;
    }

    // Checks that a given DamagedEvent matches with
    // "If a creature you control dealing combat damage to a player"
    private static boolean checkDamagedEvent(DamagedEvent event, UUID controllerId, Game game) {
        if (event == null) {
            return false;
        }
        UUID sourceId = event.getSourceId();
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(sourceId);
        UUID targetId = event.getTargetId();
        Player playerDealtDamage = game.getPlayer(targetId);
        return sourcePermanent != null
                && sourcePermanent.isCreature(game)
                && sourcePermanent.isControlledBy(controllerId)
                && event.isCombatDamage()
                && playerDealtDamage != null;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}
