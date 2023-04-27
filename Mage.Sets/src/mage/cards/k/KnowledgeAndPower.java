
package mage.cards.k;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class KnowledgeAndPower extends CardImpl {

    public KnowledgeAndPower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{R}");


        // Whenever you scry, you may pay 2. If you do. Knowledge and Power deals 2 damage to any target.
        this.addAbility(new ScryTriggeredAbility() );

    }

    private KnowledgeAndPower(final KnowledgeAndPower card) {
        super(card);
    }

    @Override
    public KnowledgeAndPower copy() {
        return new KnowledgeAndPower(this);
    }
}

class ScryTriggeredAbility extends TriggeredAbilityImpl {

    public ScryTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new DamageTargetEffect(2), new GenericManaCost(2)), false);
        this.addTarget(new TargetAnyTarget());
        setTriggerPhrase("Whenever you scry, ");
    }

    public ScryTriggeredAbility(final ScryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ScryTriggeredAbility copy() {
        return new ScryTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SCRIED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId());
    }
}
