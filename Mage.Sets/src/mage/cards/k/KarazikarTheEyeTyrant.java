package mage.cards.k;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.*;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KarazikarTheEyeTyrant extends CardImpl {

    public KarazikarTheEyeTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEHOLDER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever you attack a player, tap target creature that player controls and goad it.
        this.addAbility(new KarazikarTheEyeTyrantFirstTriggeredAbility());

        // Whenever an opponent attacks another one of your opponents, you and the attacking player each draw a card and lose 1 life.
        this.addAbility(new KarazikarTheEyeTyrantSecondTriggeredAbility());
    }

    private KarazikarTheEyeTyrant(final KarazikarTheEyeTyrant card) {
        super(card);
    }

    @Override
    public KarazikarTheEyeTyrant copy() {
        return new KarazikarTheEyeTyrant(this);
    }
}

class KarazikarTheEyeTyrantFirstTriggeredAbility extends TriggeredAbilityImpl {

    KarazikarTheEyeTyrantFirstTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TapTargetEffect(), false);
        this.addEffect(new GoadTargetEffect());
    }

    private KarazikarTheEyeTyrantFirstTriggeredAbility(final KarazikarTheEyeTyrantFirstTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KarazikarTheEyeTyrantFirstTriggeredAbility copy() {
        return new KarazikarTheEyeTyrantFirstTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DEFENDER_ATTACKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        Player player = game.getPlayer(event.getTargetId());
        if (player == null) {
            return false;
        }
        FilterPermanent filter = new FilterCreaturePermanent("creature controlled by " + player.getName());
        filter.add(new ControllerIdPredicate(player.getId()));
        this.getTargets().clear();
        this.addTarget(new TargetPermanent(filter));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you attack a player, tap target creature that player controls and goad it.";
    }
}

class KarazikarTheEyeTyrantSecondTriggeredAbility extends TriggeredAbilityImpl {

    KarazikarTheEyeTyrantSecondTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
        this.addEffect(new LoseLifeSourceControllerEffect(1));
        this.addEffect(new DrawCardTargetEffect(1));
        this.addEffect(new LoseLifeTargetEffect(1));
    }

    private KarazikarTheEyeTyrantSecondTriggeredAbility(final KarazikarTheEyeTyrantSecondTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KarazikarTheEyeTyrantSecondTriggeredAbility copy() {
        return new KarazikarTheEyeTyrantSecondTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DEFENDER_ATTACKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Set<UUID> opponents = game.getOpponents(getControllerId());
        if (!opponents.contains(event.getPlayerId()) || !opponents.contains(event.getTargetId())) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent attacks another one of your opponents, " +
                "you and the attacking player each draw a card and lose 1 life.";
    }
}
