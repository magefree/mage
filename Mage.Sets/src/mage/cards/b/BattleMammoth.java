package mage.cards.b;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BattleMammoth extends CardImpl {

    public BattleMammoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.ELEPHANT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a permanent you control becomes the target of a spell or ability an opponent controls, you may draw a card.
        this.addAbility(new BattleMammothTriggeredAbility());

        // Foretell {2}{G}{G}
        this.addAbility(new ForetellAbility(this, "{2}{G}{G}"));
    }

    private BattleMammoth(final BattleMammoth card) {
        super(card);
    }

    @Override
    public BattleMammoth copy() {
        return new BattleMammoth(this);
    }
}

class BattleMammothTriggeredAbility extends TriggeredAbilityImpl {

    BattleMammothTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), true);
    }

    private BattleMammothTriggeredAbility(final BattleMammothTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BattleMammothTriggeredAbility copy() {
        return new BattleMammothTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null || !permanent.isControlledBy(this.getControllerId())) {
            return false;
        }
        Player targetter = game.getPlayer(event.getPlayerId());
        Object object = game.getObject(event.getSourceId());
        return object != null && targetter != null && targetter.hasOpponent(this.getControllerId(), game);
    }

    @Override
    public String getRule() {
        return "Whenever a permanent you control becomes the target of a spell or ability an opponent controls, you may draw a card.";
    }
}
