package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksPlayerWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.*;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;
import mage.target.targetpointer.FixedTarget;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KarazikarTheEyeTyrant extends CardImpl {
    FilterPermanent filter = new FilterCreaturePermanent("creature that player controls");

    public KarazikarTheEyeTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEHOLDER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever you attack a player, tap target creature that player controls and goad it.
        Ability ability = new AttacksPlayerWithCreaturesTriggeredAbility(new TapTargetEffect(), SetTargetPointer.PLAYER);
        ability.addEffect(new GoadTargetEffect().setText("goad it. " + GoadTargetEffect.goadReminderText).concatBy("and"));
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster());
        this.addAbility(ability);

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
