package mage.cards.v;

import mage.MageInt;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.common.SourceInGraveyardCondition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.PutOnLibrarySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedBatchForOnePermanentEvent;
import mage.game.events.DamagedBatchForOnePlayerEvent;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author North
 */
public final class VengefulPharaoh extends CardImpl {

    public VengefulPharaoh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Deathtouch (Any amount of damage this deals to a creature is enough to destroy it.)
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever combat damage is dealt to you or a planeswalker you control, if Vengeful Pharaoh is in your graveyard, destroy target attacking creature, then put Vengeful Pharaoh on top of your library.
        this.addAbility(new VengefulPharaohTriggeredAbility());
    }

    private VengefulPharaoh(final VengefulPharaoh card) {
        super(card);
    }

    @Override
    public VengefulPharaoh copy() {
        return new VengefulPharaoh(this);
    }
}

class VengefulPharaohTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedEvent> {

    VengefulPharaohTriggeredAbility() {
        super(Zone.GRAVEYARD, new DestroyTargetEffect(), false);
        this.addTarget(new TargetAttackingCreature());
        this.addEffect(new PutOnLibrarySourceEffect(true).setText(", then put this card on top of your library"));
        this.withInterveningIf(SourceInGraveyardCondition.instance);
        setTriggerPhrase("Whenever combat damage is dealt to you or a planeswalker you control, ");
    }

    private VengefulPharaohTriggeredAbility(final VengefulPharaohTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VengefulPharaohTriggeredAbility copy() {
        return new VengefulPharaohTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        // If multiple creatures deal combat damage to you simultaneously, Vengeful Pharaoh will only trigger once.
        // (2011-09-22)
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PLAYER
                || event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if ((event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PLAYER
                && event.getTargetId().equals(this.getControllerId()))) {
            DamagedBatchForOnePlayerEvent dEvent = (DamagedBatchForOnePlayerEvent) event;
            return dEvent.isCombatDamage() && dEvent.getAmount() > 0;
        }
        if (event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PERMANENT) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            DamagedBatchForOnePermanentEvent dEvent = (DamagedBatchForOnePermanentEvent) event;
            return permanent != null
                    && permanent.isPlaneswalker(game)
                    && permanent.isControlledBy(this.getControllerId())
                    && dEvent.isCombatDamage() && dEvent.getAmount() > 0;
        }
        return false;
    }
}
