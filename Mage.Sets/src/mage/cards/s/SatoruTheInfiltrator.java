package mage.cards.s;

import mage.MageInt;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.stack.Spell;

import java.util.List;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class SatoruTheInfiltrator extends CardImpl {

    public SatoruTheInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever Satoru, the Infiltrator and/or one or more other nontoken creatures enter the battlefield under your control, if none of them were cast or no mana was spent to cast them, draw a card.
        this.addAbility(new SatoruTheInfiltratorTriggeredAbility());
    }

    private SatoruTheInfiltrator(final SatoruTheInfiltrator card) {
        super(card);
    }

    @Override
    public SatoruTheInfiltrator copy() {
        return new SatoruTheInfiltrator(this);
    }
}

class SatoruTheInfiltratorTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<ZoneChangeEvent>  {

    SatoruTheInfiltratorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
        this.setTriggerPhrase("Whenever {this} and/or one or more other nontoken creatures "
                + "enter the battlefield under your control, if none of them were cast or no mana was spent to cast them, ");
    }

    protected SatoruTheInfiltratorTriggeredAbility(final SatoruTheInfiltratorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SatoruTheInfiltratorTriggeredAbility copy() {
        return new SatoruTheInfiltratorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_BATCH;
    }

    @Override
    public boolean checkEvent(ZoneChangeEvent event, Game game) {
        if (event.getToZone() != Zone.BATTLEFIELD) {
            return false;
        }
        Permanent permanent = event.getTarget();
        if (permanent == null || !permanent.isControlledBy(getControllerId())) {
            return false;
        }
        return permanent.getId().equals(getSourceId()) || (permanent.isCreature(game) && !(permanent instanceof PermanentToken));
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        List<ZoneChangeEvent> moved = getFilteredEvents((ZoneChangeBatchEvent) event, game);
        if (moved.isEmpty()) {
            return false;
        }
        // At this point, we have at least one event matching
        // "Whenever {this} and/or one or more other nontoken creatures enter the battlefield under your control"
        // Now to check that none were cast using mana
        for (ZoneChangeEvent zce : moved) {
            Spell spell = game.getSpellOrLKIStack(zce.getTargetId());
            if (spell != null && spell.getStackAbility().getManaCostsToPay().getUsedManaToPay().count() > 0) {
                return false; // found one that was cast using mana, so no triggering.
            }
        }
        return true; // all relevant permanents passed the spell mana check, so triggering.
    }
}
