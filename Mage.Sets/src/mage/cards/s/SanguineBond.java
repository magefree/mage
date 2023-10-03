package mage.cards.s;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author North
 */
public final class SanguineBond extends CardImpl {

    public SanguineBond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        // Whenever you gain life, target opponent loses that much life.
        SanguineBondTriggeredAbility ability = new SanguineBondTriggeredAbility();
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private SanguineBond(final SanguineBond card) {
        super(card);
    }

    @Override
    public SanguineBond copy() {
        return new SanguineBond(this);
    }
}

class SanguineBondTriggeredAbility extends TriggeredAbilityImpl {

    public SanguineBondTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    private SanguineBondTriggeredAbility(final SanguineBondTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SanguineBondTriggeredAbility copy() {
        return new SanguineBondTriggeredAbility(this);
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
