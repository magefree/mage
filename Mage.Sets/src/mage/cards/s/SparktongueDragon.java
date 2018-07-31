package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.SendOptionUsedEventEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author TheElk801
 */
public final class SparktongueDragon extends CardImpl {

    public SparktongueDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Sparktongue Dragon enters the battlefield, you may pay {2}{R}. When you do, it deals 3 damage to any target.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoIfCostPaid(
                        new SparktongueDragonCreateReflexiveTriggerEffect(),
                        new ManaCostsImpl("{2}{R}"),
                        "Pay {2}{R} to deal 3 damage?"
                ).setText("you may pay {2}{R}. When you do, it deals 3 damage to any target")
        ));
    }

    public SparktongueDragon(final SparktongueDragon card) {
        super(card);
    }

    @Override
    public SparktongueDragon copy() {
        return new SparktongueDragon(this);
    }
}

class SparktongueDragonCreateReflexiveTriggerEffect extends OneShotEffect {

    public SparktongueDragonCreateReflexiveTriggerEffect() {
        super(Outcome.Benefit);
        this.staticText = "When you do, it deals 3 damage to any target";
    }

    public SparktongueDragonCreateReflexiveTriggerEffect(final SparktongueDragonCreateReflexiveTriggerEffect effect) {
        super(effect);
    }

    @Override
    public SparktongueDragonCreateReflexiveTriggerEffect copy() {
        return new SparktongueDragonCreateReflexiveTriggerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addDelayedTriggeredAbility(new SparktongueDragonReflexiveTriggeredAbility(), source);
        return new SendOptionUsedEventEffect().apply(game, source);
    }
}

class SparktongueDragonReflexiveTriggeredAbility extends DelayedTriggeredAbility {

    public SparktongueDragonReflexiveTriggeredAbility() {
        super(new DamageTargetEffect(3), Duration.OneUse, true);
        this.addTarget(new TargetAnyTarget());
    }

    public SparktongueDragonReflexiveTriggeredAbility(final SparktongueDragonReflexiveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SparktongueDragonReflexiveTriggeredAbility copy() {
        return new SparktongueDragonReflexiveTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.OPTION_USED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "When you pay {2}{R}, {this} deals 3 damage to any target";
    }
}
