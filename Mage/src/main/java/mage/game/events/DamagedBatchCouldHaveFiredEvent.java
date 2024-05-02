package mage.game.events;

import mage.abilities.Ability;

/**
 * Does not contain any info on damage events, and can fire even when all damage is prevented.
 * Fire any time a DAMAGED_BATCH_FOR_ALL could have fired (combat & noncombat).
 * It is not a batch event (doesn't contain sub events), the name is a little ambiguous.
 *
 * @author Susucr
 */
public class DamagedBatchCouldHaveFiredEvent extends GameEvent {

    public DamagedBatchCouldHaveFiredEvent(boolean combat) {
        super(EventType.DAMAGED_BATCH_COULD_HAVE_FIRED, null, (Ability) null, null, 0, combat);
    }
}
