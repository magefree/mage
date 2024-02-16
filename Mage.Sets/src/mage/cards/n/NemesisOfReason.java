

package mage.cards.n;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.MillCardsTargetEffect;
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
 * @author Loki
 */
public final class NemesisOfReason extends CardImpl {

    public NemesisOfReason(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");
        this.subtype.add(SubType.LEVIATHAN);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(7);

        // Whenever Nemesis of Reason attacks, defending player puts the top ten cards of their library into their graveyard.
        this.addAbility(new NemesisOfReasonTriggeredAbility(new MillCardsTargetEffect(10)));
    }

    private NemesisOfReason(final NemesisOfReason card) {
        super(card);
    }

    @Override
    public NemesisOfReason copy() {
        return new NemesisOfReason(this);
    }
}

class NemesisOfReasonTriggeredAbility extends TriggeredAbilityImpl {

    NemesisOfReasonTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    private NemesisOfReasonTriggeredAbility(final NemesisOfReasonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NemesisOfReasonTriggeredAbility copy() {
        return new NemesisOfReasonTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            UUID defenderId = game.getCombat().getDefendingPlayerId(this.getSourceId(), game);
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(defenderId));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks, defending player mills ten cards.";
    }
}
