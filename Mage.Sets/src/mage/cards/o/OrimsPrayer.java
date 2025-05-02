package mage.cards.o;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author L_J
 */
public final class OrimsPrayer extends CardImpl {

    public OrimsPrayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");

        // Whenever one or more creatures attack you, you gain 1 life for each attacking creature.
        this.addAbility(new OrimsPrayerTriggeredAbility());
    }

    private OrimsPrayer(final OrimsPrayer card) {
        super(card);
    }

    @Override
    public OrimsPrayer copy() {
        return new OrimsPrayer(this);
    }
}

class OrimsPrayerTriggeredAbility extends TriggeredAbilityImpl {

    public OrimsPrayerTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    private OrimsPrayerTriggeredAbility(final OrimsPrayerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OrimsPrayerTriggeredAbility copy() {
        return new OrimsPrayerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        boolean applied = false;
        Player controller = game.getPlayer(getControllerId());
        if (controller == null) {
            return false;
        }
        int numberAttackingController = 0;
        for (UUID attackersId : game.getCombat().getAttackers()) {
            Permanent attackingCreature = game.getPermanent(attackersId);
            if (attackingCreature != null
                    && game.getCombat().getDefenderId(attackersId) == this.getControllerId()) {
                numberAttackingController += 1;
                applied = true;
            }
        }
        if (applied && numberAttackingController > 0) {
            this.getEffects().clear();
            this.getEffects().add(new GainLifeEffect(numberAttackingController));
        }
        return applied;
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures attack you, you gain 1 life for each attacking creature.";
    }
}
