package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;

/**
 *
 * @author weirddan455
 */
public final class MagusOfTheBridge extends CardImpl {

    public MagusOfTheBridge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever a nontoken creature is put into your graveyard from the battlefield, create a 2/2 black Zombie creature token.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new CreateTokenEffect(new ZombieToken()), false, StaticFilters.FILTER_CREATURE_NON_TOKEN, false, true
        ));

        // When a creature is put into an opponent's graveyard from the battlefield, exile Magus of the Bridge.
        this.addAbility(new MagusOfTheBridgeTriggeredAbility());
    }

    private MagusOfTheBridge(final MagusOfTheBridge card) {
        super(card);
    }

    @Override
    public MagusOfTheBridge copy() {
        return new MagusOfTheBridge(this);
    }
}

class MagusOfTheBridgeTriggeredAbility extends TriggeredAbilityImpl {

    public MagusOfTheBridgeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ExileSourceEffect());
        setTriggerPhrase("When a creature is put into an opponent's graveyard from the battlefield, ");
    }

    private MagusOfTheBridgeTriggeredAbility(final MagusOfTheBridgeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MagusOfTheBridgeTriggeredAbility copy() {
        return new MagusOfTheBridgeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()) {
            Permanent permanent = zEvent.getTarget();
            Player controller = game.getPlayer(getControllerId());
            return permanent != null && controller != null
                    && permanent.isCreature(game) && controller.hasOpponent(permanent.getOwnerId(), game);
        }
        return false;
    }
}
