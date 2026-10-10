package mage.cards.w;

import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLoyaltyCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackAbility;

import java.util.UUID;

/**
 * @author muz
 */
public final class WayOfTheMindSculptor extends CardImpl {

    public WayOfTheMindSculptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}");
        this.supertype.add(SuperType.LEGENDARY);

        // When Way of the Mind Sculptor enters, empower Jace 5.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EmpowerJaceEffect(5)));

        // Whenever you activate a loyalty ability, if you removed two or more loyalty counters to activate it, draw a card.
        this.addAbility(new WayOfTheMindSculptorTriggeredAbility());
    }

    private WayOfTheMindSculptor(final WayOfTheMindSculptor card) {
        super(card);
    }

    @Override
    public WayOfTheMindSculptor copy() {
        return new WayOfTheMindSculptor(this);
    }
}

class WayOfTheMindSculptorTriggeredAbility extends TriggeredAbilityImpl {

    WayOfTheMindSculptorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        setTriggerPhrase("Whenever you activate a loyalty ability, if you removed two or more loyalty counters to activate it, ");
    }

    private WayOfTheMindSculptorTriggeredAbility(final WayOfTheMindSculptorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WayOfTheMindSculptorTriggeredAbility copy() {
        return new WayOfTheMindSculptorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
        if (stackAbility == null || !(stackAbility.getStackAbility() instanceof LoyaltyAbility)) {
            return false;
        }
        int removed = 0;
        for (Cost cost : stackAbility.getStackAbility().getCosts()) {
            if (cost instanceof PayLoyaltyCost && ((PayLoyaltyCost) cost).getAmount() < 0) {
                removed -= ((PayLoyaltyCost) cost).getAmount();
            }
        }
        return removed >= 2;
    }
}
