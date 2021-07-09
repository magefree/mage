package mage.cards.n;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.EffectKeyValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.NestOfScarabsBlackInsectToken;

import java.util.UUID;

/**
 * @author stravant
 */
public final class NestOfScarabs extends CardImpl {

    public NestOfScarabs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // Whenever you put one or more -1/-1 counters on a creature, create that many 1/1 black Insect tokens.
        this.addAbility(new NestOfScarabsTriggeredAbility());

    }

    private NestOfScarabs(final NestOfScarabs card) {
        super(card);
    }

    @Override
    public NestOfScarabs copy() {
        return new NestOfScarabs(this);
    }
}

class NestOfScarabsTriggeredAbility extends TriggeredAbilityImpl {

    NestOfScarabsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new NestOfScarabsBlackInsectToken(), new EffectKeyValue("countersAdded")));
    }

    NestOfScarabsTriggeredAbility(final NestOfScarabsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        boolean weAreDoingIt = isControlledBy(event.getPlayerId());
        boolean isM1M1Counters = event.getData().equals(CounterType.M1M1.getName());
        if (weAreDoingIt && isM1M1Counters && event.getAmount() > 0) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent == null) {
                permanent = game.getPermanentEntering(event.getTargetId());
            }
            if (permanent != null && permanent.isCreature(game)) {
                getEffects().setValue("countersAdded", event.getAmount());
                return true;
            }
        }
        return false;
    }

    @Override
    public NestOfScarabsTriggeredAbility copy() {
        return new NestOfScarabsTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you put one or more -1/-1 counters on a creature, create that many 1/1 black Insect tokens.";
    }
}
