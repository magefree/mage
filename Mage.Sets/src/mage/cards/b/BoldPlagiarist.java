package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Jason Felix
 */
public final class BoldPlagiarist extends CardImpl {

    public BoldPlagiarist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Whenever an opponent puts one or more counters on a creature they control, they put the same number and kind of counters on Bold Plagiarist.
        this.addAbility(new BoldPlagiaristTriggeredAbility());
    }

    private BoldPlagiarist(final BoldPlagiarist card) {
        super(card);
    }

    @Override
    public BoldPlagiarist copy() {
        return new BoldPlagiarist(this);
    }
}

class BoldPlagiaristTriggeredAbility extends TriggeredAbilityImpl {

    BoldPlagiaristTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoldPlagiaristEffect());
    }

    private BoldPlagiaristTriggeredAbility(final BoldPlagiaristTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.getOpponents(getControllerId()).contains(event.getPlayerId())) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null
                || !permanent.isCreature(game)
                || !permanent.isControlledBy(event.getPlayerId())) {
            return false;
        }
        getEffects().setValue("counterType", event.getData());
        getEffects().setValue("counterAmount", event.getAmount());
        getEffects().setValue("playerId", event.getPlayerId());
        return true;
    }

    @Override
    public BoldPlagiaristTriggeredAbility copy() {
        return new BoldPlagiaristTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever an opponent puts one or more counters on a creature they control, " +
                "they put the same number and kind of counters on {this}.";
    }
}

class BoldPlagiaristEffect extends OneShotEffect {

    BoldPlagiaristEffect() {
        super(Outcome.Benefit);
    }

    private BoldPlagiaristEffect(final BoldPlagiaristEffect effect) {
        super(effect);
    }

    @Override
    public BoldPlagiaristEffect copy() {
        return new BoldPlagiaristEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        CounterType counterType = CounterType.findByName((String) getValue("counterType"));
        Integer amount = (Integer) getValue("counterAmount");
        UUID playerId = (UUID) getValue("playerId");
        return permanent != null
                && counterType != null
                && amount != null
                && playerId != null
                && permanent.addCounters(counterType.createInstance(amount), playerId, source, game);
    }
}
