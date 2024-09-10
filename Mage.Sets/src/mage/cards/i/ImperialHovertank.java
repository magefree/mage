package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author NinthWorld
 */
public final class ImperialHovertank extends CardImpl {

    public ImperialHovertank(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{W}{B}");
        
        this.subtype.add(SubType.TROOPER);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever a Trooper creature you control attacks, defending player loses 1 life and you gain 1 life.
        this.addAbility(new ImperialHovertankTriggeredAbility());
    }

    private ImperialHovertank(final ImperialHovertank card) {
        super(card);
    }

    @Override
    public ImperialHovertank copy() {
        return new ImperialHovertank(this);
    }
}

class ImperialHovertankTriggeredAbility extends TriggeredAbilityImpl {

    public ImperialHovertankTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1));
        this.addEffect(new GainLifeEffect(1));
    }

    private ImperialHovertankTriggeredAbility(final ImperialHovertankTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ImperialHovertankTriggeredAbility copy() {
        return new ImperialHovertankTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent source = game.getPermanent(event.getSourceId());
        if (source != null && source.isControlledBy(controllerId) && source.hasSubtype(SubType.TROOPER, game)) {
            UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(event.getSourceId(), game);
            this.getEffects().get(0).setTargetPointer(new FixedTarget(defendingPlayerId));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a Trooper creature you control attacks, defending player loses 1 life and you gain 1 life.";
    }
}