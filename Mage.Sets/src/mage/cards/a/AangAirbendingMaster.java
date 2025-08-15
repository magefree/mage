package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersControllerCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.abilities.effects.keyword.AirbendTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.AllyToken;
import mage.target.TargetPermanent;

/**
 *
 * @author Grath
 */
public final class AangAirbendingMaster extends CardImpl {

    private static final DynamicValue xValue = new CountersControllerCount(CounterType.EXPERIENCE);

    public AangAirbendingMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Aang enters, airbend another target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AirbendTargetEffect(), false);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);

        // Whenever one or more creatures you control leave the battlefield without dying, you get an experience counter.
        this.addAbility(new AangAirbendingMasterTriggeredAbility());

        // At the beginning of your upkeep, create a 1/1 white Ally creature token for each experience counter you have.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new AllyToken(), xValue)));
    }

    private AangAirbendingMaster(final AangAirbendingMaster card) {
        super(card);
    }

    @Override
    public AangAirbendingMaster copy() {
        return new AangAirbendingMaster(this);
    }
}

class AangAirbendingMasterTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<ZoneChangeEvent> {

    AangAirbendingMasterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersPlayersEffect(CounterType.EXPERIENCE.createInstance(), TargetController.YOU));
        setTriggerPhrase("Whenever one or more creatures you control leave the battlefield without dying, ");
    }

    private AangAirbendingMasterTriggeredAbility(final AangAirbendingMasterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AangAirbendingMasterTriggeredAbility copy() {
        return new AangAirbendingMasterTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_BATCH;
    }

    @Override
    public boolean checkEvent(ZoneChangeEvent event, Game game) {
        if (event.getFromZone() != Zone.BATTLEFIELD || event.getToZone() == Zone.GRAVEYARD) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        return permanent != null && permanent.isCreature(game) && permanent.isControlledBy(getControllerId());
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return !getFilteredEvents((ZoneChangeBatchEvent) event, game).isEmpty();
    }
}