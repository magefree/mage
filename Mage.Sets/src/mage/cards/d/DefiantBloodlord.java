
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class DefiantBloodlord extends CardImpl {

    public DefiantBloodlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever you gain life, target opponent loses that much life.
        DefiantBloodlordTriggeredAbility ability = new DefiantBloodlordTriggeredAbility();
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private DefiantBloodlord(final DefiantBloodlord card) {
        super(card);
    }

    @Override
    public DefiantBloodlord copy() {
        return new DefiantBloodlord(this);
    }
}

class DefiantBloodlordTriggeredAbility extends TriggeredAbilityImpl {

    public DefiantBloodlordTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    private DefiantBloodlordTriggeredAbility(final DefiantBloodlordTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DefiantBloodlordTriggeredAbility copy() {
        return new DefiantBloodlordTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAINED_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.controllerId)) {
            this.getEffects().clear();
            this.addEffect(new LoseLifeTargetEffect(event.getAmount()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you gain life, target opponent loses that much life.";
    }
}
