package mage.cards.m;

import mage.MageInt;
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
import mage.game.events.DamagedEvent;
import mage.game.events.DamagedPlayerBatchEvent;
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

class MalcolmKeenEyedNavigatorTriggeredAbility extends TriggeredAbilityImpl {

    MalcolmKeenEyedNavigatorTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    private MalcolmKeenEyedNavigatorTriggeredAbility(final MalcolmKeenEyedNavigatorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER_BATCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerBatchEvent dEvent = (DamagedPlayerBatchEvent) event;
        Set<UUID> opponents = new HashSet<>();
        for (DamagedEvent damagedEvent : dEvent.getEvents()) {
            Permanent permanent = game.getPermanent(damagedEvent.getSourceId());
            if (permanent == null
                    || !permanent.isControlledBy(getControllerId())
                    || !permanent.hasSubtype(SubType.PIRATE, game)
                    || !game.getOpponents(getControllerId()).contains(damagedEvent.getTargetId())) {
                continue;
            }
            opponents.add(damagedEvent.getTargetId());
        }
        if (opponents.size() < 1) {
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
