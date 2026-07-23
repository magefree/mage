package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterActivatedOrTriggeredAbility;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;

/**
 *
 * @author nandmp
 */
public final class LokiGodOfMischief extends CardImpl {

    public LokiGodOfMischief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.subtype.add(SubType.SORCERER);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever a player or permanent becomes the target of an ability you control, draw a card. This ability triggers only once each turn.
        TriggeredAbility ability = new LokiGodOfMischiefTriggeredAbility();
        ability.setTriggersLimitEachTurn(1);
        this.addAbility(ability);
    }

    private LokiGodOfMischief(final LokiGodOfMischief card) {
        super(card);
    }

    @Override
    public LokiGodOfMischief copy() {
        return new LokiGodOfMischief(this);
    }
}

class LokiGodOfMischiefTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterActivatedOrTriggeredAbility filter = new FilterActivatedOrTriggeredAbility("an ability you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    LokiGodOfMischiefTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
        setTriggerPhrase("Whenever a player or permanent becomes the target of an ability you control, ");
    }

    private LokiGodOfMischiefTriggeredAbility(final LokiGodOfMischiefTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LokiGodOfMischiefTriggeredAbility copy() {
        return new LokiGodOfMischiefTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null && game.getPlayer(event.getTargetId()) == null) {
            return false;
        }

        StackObject targetingObject = game.findTargetingStackObject(this.getId().toString(), event);
        return targetingObject != null && filter.match(targetingObject, getControllerId(), this, game);
    }
}
