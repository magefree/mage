
package mage.cards.a;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class ArmWithAether extends CardImpl {

    public ArmWithAether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");


        // Until end of turn, creatures you control gain "Whenever this creature deals damage to an opponent, you may return target creature that player controls to its owner's hand."
        Effect effect = new GainAbilityControlledEffect(new ArmWithAetherTriggeredAbility(), Duration.EndOfTurn, new FilterCreaturePermanent());
        effect.setText("Until end of turn, creatures you control gain \"Whenever this creature deals damage to an opponent, you may return target creature that player controls to its owner's hand.\"");
        this.getSpellAbility().addEffect(effect);
    }

    private ArmWithAether(final ArmWithAether card) {
        super(card);
    }

    @Override
    public ArmWithAether copy() {
        return new ArmWithAether(this);
    }
}

class ArmWithAetherTriggeredAbility extends TriggeredAbilityImpl {

    public ArmWithAetherTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), true);
    }

    private ArmWithAetherTriggeredAbility(final ArmWithAetherTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ArmWithAetherTriggeredAbility copy() {
        return new ArmWithAetherTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player opponent = game.getPlayer(event.getPlayerId());
        if (opponent != null && event.getSourceId().equals(this.sourceId)) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature " + opponent.getLogName() + " controls");
            filter.add(new ControllerIdPredicate(opponent.getId()));

            this.getTargets().clear();
            this.addTarget(new TargetCreaturePermanent(filter));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever this creature deals damage to an opponent, you may return target creature that player controls to its owner's hand.";
    }
}
