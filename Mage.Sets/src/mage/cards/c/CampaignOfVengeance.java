
package mage.cards.c;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
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
 * @author LevelX2
 */
public final class CampaignOfVengeance extends CardImpl {

    public CampaignOfVengeance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}{B}");

        // Whenever a creature you control attacks, defending player loses 1 life and you gain 1 life.
        this.addAbility(new CampaignOfVengeanceTriggeredAbility());
    }

    private CampaignOfVengeance(final CampaignOfVengeance card) {
        super(card);
    }

    @Override
    public CampaignOfVengeance copy() {
        return new CampaignOfVengeance(this);
    }
}

class CampaignOfVengeanceTriggeredAbility extends TriggeredAbilityImpl {

    public CampaignOfVengeanceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1));
        this.addEffect(new GainLifeEffect(1));
    }

    private CampaignOfVengeanceTriggeredAbility(final CampaignOfVengeanceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CampaignOfVengeanceTriggeredAbility copy() {
        return new CampaignOfVengeanceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent source = game.getPermanent(event.getSourceId());
        if (source != null && source.isControlledBy(controllerId)) {
            UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(event.getSourceId(), game);
            this.getEffects().get(0).setTargetPointer(new FixedTarget(defendingPlayerId));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control attacks, defending player loses 1 life and you gain 1 life.";
    }
}
