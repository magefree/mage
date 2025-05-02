package mage.cards.m;

import mage.MageInt;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedBatchForPlayersEvent;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MalcolmKeenEyedNavigator extends CardImpl {

    public MalcolmKeenEyedNavigator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SIREN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever one or more Pirates you control deal damage to your opponents, you create a Treasure token for each opponent dealt damage.
        this.addAbility(new MalcolmKeenEyedNavigatorTriggeredAbility());

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private MalcolmKeenEyedNavigator(final MalcolmKeenEyedNavigator card) {
        super(card);
    }

    @Override
    public MalcolmKeenEyedNavigator copy() {
        return new MalcolmKeenEyedNavigator(this);
    }
}

class MalcolmKeenEyedNavigatorTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPlayerEvent> {

    MalcolmKeenEyedNavigatorTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    private MalcolmKeenEyedNavigatorTriggeredAbility(final MalcolmKeenEyedNavigatorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_PLAYERS;
    }

    @Override
    public boolean checkEvent(DamagedPlayerEvent event, Game game) {
        if (!game.getOpponents(getControllerId()).contains(event.getTargetId())) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        return permanent != null
                && permanent.isControlledBy(getControllerId())
                && permanent.hasSubtype(SubType.PIRATE, game);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Set<UUID> opponents = new HashSet<>();
        getFilteredEvents((DamagedBatchForPlayersEvent) event, game)
                .stream()
                .map(GameEvent::getTargetId)
                .forEach(opponents::add);
        if (opponents.isEmpty()) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new CreateTokenEffect(new TreasureToken(), opponents.size()));
        return true;
    }

    @Override
    public MalcolmKeenEyedNavigatorTriggeredAbility copy() {
        return new MalcolmKeenEyedNavigatorTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever one or more Pirates you control deal damage to your opponents, " +
                "you create a Treasure token for each opponent dealt damage.";
    }
}
