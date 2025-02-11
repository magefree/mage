package mage.cards.r;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RecklessVelocitaur extends CardImpl {

    public RecklessVelocitaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever this creature saddles a Mount or crews a Vehicle during your main phase, that Mount or Vehicle gets +2/+0 and gains trample until end of turn.
        this.addAbility(new RecklessVelocitaurTriggeredAbility());
    }

    private RecklessVelocitaur(final RecklessVelocitaur card) {
        super(card);
    }

    @Override
    public RecklessVelocitaur copy() {
        return new RecklessVelocitaur(this);
    }
}

class RecklessVelocitaurTriggeredAbility extends TriggeredAbilityImpl {

    RecklessVelocitaurTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostTargetEffect(2, 0).setText("that Mount or Vehicle gets +2/+0"));
        this.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance()).setText("and gains trample until end of turn"));
        this.setTriggerPhrase("Whenever {this} saddles a Mount or crews a Vehicle during your main phase, ");
    }

    private RecklessVelocitaurTriggeredAbility(final RecklessVelocitaurTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RecklessVelocitaurTriggeredAbility copy() {
        return new RecklessVelocitaurTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case SADDLED_MOUNT:
            case CREWED_VEHICLE:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!this.isControlledBy(game.getActivePlayerId())
                || !game.isMainPhase()
                || !event.getTargetId().equals(this.getSourceId())) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(event.getSourceId()));
        return true;
    }
}
