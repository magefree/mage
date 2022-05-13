package mage.abilities.common;

import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.TriggeredManaAbility;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.TappedForManaEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * Mana triggered ability (use case: you must produce new mana on mana taps)
 *
 * @author LevelX2
 */
public class TapForManaAllTriggeredManaAbility extends TriggeredManaAbility {

    private final FilterPermanent filter;
    private final SetTargetPointer setTargetPointer;

    public TapForManaAllTriggeredManaAbility(ManaEffect effect, FilterPermanent filter, SetTargetPointer setTargetPointer) {
        super(Zone.BATTLEFIELD, effect);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
    }

    public TapForManaAllTriggeredManaAbility(TapForManaAllTriggeredManaAbility ability) {
        super(ability);
        this.filter = ability.filter.copy();
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        TappedForManaEvent manaEvent = ((TappedForManaEvent) event);
        Permanent permanent = manaEvent.getPermanent();
        if (permanent == null || !filter.match(permanent, getControllerId(), this, game)) {
            return false;
        }
        getEffects().setValue("mana", manaEvent.getMana());
        getEffects().setValue("tappedPermanent", permanent);
        switch (setTargetPointer) {
            case PERMANENT:
                getEffects().setTargetPointer(new FixedTarget(permanent.getId(), permanent.getZoneChangeCounter(game)));
                break;
            case PLAYER:
                getEffects().setTargetPointer(new FixedTarget(permanent.getControllerId()));
                break;
        }
        return true;
    }

    @Override
    public TapForManaAllTriggeredManaAbility copy() {
        return new TapForManaAllTriggeredManaAbility(this);
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever " + filter.getMessage() + " for mana, " ;
    }
}