package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.SpecialAction;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateSpecialActionEffect;
import mage.abilities.effects.common.RemoveSpecialActionEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class YavimayaBloomsage extends PrepareCard {

    public YavimayaBloomsage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}", "Channel", new CardType[]{CardType.SORCERY}, "{G}{G}");

        this.subtype.add(SubType.DRYAD);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your end step, put a +1/+1 counter on target creature you control. Then if that creature has power 7 or greater, this creature becomes prepared.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addEffect(new ConditionalOneShotEffect(
            new BecomePreparedSourceEffect(),
            YavimayaBloomsageCondition.instance,
            "Then if that creature has toughness 7 or greater, this creature becomes prepared"
        ));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Channel
        // Sorcery {G}{G}
        // Until end of turn, any time you could activate a mana ability, you may pay 1 life. If you do, add {C}.
        this.getSpellCard().getSpellAbility().addEffect(new ChannelEffect());
    }

    private YavimayaBloomsage(final YavimayaBloomsage card) {
        super(card);
    }

    @Override
    public YavimayaBloomsage copy() {
        return new YavimayaBloomsage(this);
    }
}

enum YavimayaBloomsageCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            return permanent.getToughness().getValue() >= 7;
        }
        return false;
    }

    @Override
    public String toString() {
        return "that creature has toughness 7 or greater";
    }
}

class ChannelEffect extends OneShotEffect {

    ChannelEffect() {
        super(Outcome.PutManaInPool);
        this.staticText = "Until end of turn, any time you could activate a mana ability, you may pay 1 life. If you do, add {C}";
    }

    private ChannelEffect(final ChannelEffect effect) {
        super(effect);
    }

    @Override
    public ChannelEffect copy() {
        return new ChannelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        SpecialAction specialAction = new ChannelSpecialAction();
        new CreateSpecialActionEffect(specialAction).apply(game, source);

        // Create a hidden delayed triggered ability to remove the special action at end of turn.
        new CreateDelayedTriggeredAbilityEffect(new ChannelDelayedTriggeredAbility(specialAction.getId()), false).apply(game, source);
        return true;
    }
}

class ChannelSpecialAction extends SpecialAction {

    ChannelSpecialAction() {
        super();
        this.abilityType = AbilityType.SPECIAL_MANA_PAYMENT;
        this.addCost(new PayLifeCost(1));
        this.addEffect(new BasicManaEffect(Mana.ColorlessMana(1)));
    }

    private ChannelSpecialAction(final ChannelSpecialAction ability) {
        super(ability);
    }

    @Override
    public ChannelSpecialAction copy() {
        return new ChannelSpecialAction(this);
    }
}

class ChannelDelayedTriggeredAbility extends DelayedTriggeredAbility {

    ChannelDelayedTriggeredAbility(UUID specialActionId) {
        super(new RemoveSpecialActionEffect(specialActionId), Duration.OneUse);
        this.usesStack = false;
        this.setRuleVisible(false);
    }

    private ChannelDelayedTriggeredAbility(final ChannelDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ChannelDelayedTriggeredAbility copy() {
        return new ChannelDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CLEANUP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }
}
