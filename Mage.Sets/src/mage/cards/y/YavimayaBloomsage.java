package mage.cards.y;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.SpecialAction;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateSpecialActionEffect;
import mage.abilities.effects.common.RemoveSpecialActionEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.cards.PrepareUtil;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class YavimayaBloomsage extends PrepareCard {

    public YavimayaBloomsage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}", "Channel", CardType.SORCERY, "{G}{G}");

        this.subtype.add(SubType.DRYAD);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your end step, put a +1/+1 counter on target creature you control.
        // Then if that creature has power 7 or greater, this creature becomes prepared.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new YavimayaBloomsageEffect());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Channel
        // Sorcery {G}{G}
        // Until end of turn, any time you could activate a mana ability, you may pay 1 life. If you do, add {C}.
        this.getSpellCard().getSpellAbility().addEffect(new YavimayaBloomsageChannelEffect());
    }

    private YavimayaBloomsage(final YavimayaBloomsage card) {
        super(card);
    }

    @Override
    public YavimayaBloomsage copy() {
        return new YavimayaBloomsage(this);
    }
}

class YavimayaBloomsageChannelEffect extends OneShotEffect {

    YavimayaBloomsageChannelEffect() {
        super(Outcome.PutManaInPool);
        staticText = "until end of turn, any time you could activate a mana ability, you may pay 1 life. If you do, add {C}";
    }

    private YavimayaBloomsageChannelEffect(final YavimayaBloomsageChannelEffect effect) {
        super(effect);
    }

    @Override
    public YavimayaBloomsageChannelEffect copy() {
        return new YavimayaBloomsageChannelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        SpecialAction specialAction = new YavimayaBloomsageChannelSpecialAction();
        new CreateSpecialActionEffect(specialAction).apply(game, source);
        new CreateDelayedTriggeredAbilityEffect(
                new YavimayaBloomsageChannelDelayedTriggeredAbility(specialAction.getId()), false
        ).apply(game, source);
        return true;
    }
}

class YavimayaBloomsageChannelSpecialAction extends SpecialAction {

    YavimayaBloomsageChannelSpecialAction() {
        super();
        this.abilityType = AbilityType.SPECIAL_MANA_PAYMENT;
        this.addCost(new PayLifeCost(1));
        this.addEffect(new BasicManaEffect(Mana.ColorlessMana(1)));
    }

    private YavimayaBloomsageChannelSpecialAction(final YavimayaBloomsageChannelSpecialAction ability) {
        super(ability);
    }

    @Override
    public YavimayaBloomsageChannelSpecialAction copy() {
        return new YavimayaBloomsageChannelSpecialAction(this);
    }
}

class YavimayaBloomsageChannelDelayedTriggeredAbility extends DelayedTriggeredAbility {

    YavimayaBloomsageChannelDelayedTriggeredAbility(UUID specialActionId) {
        super(new RemoveSpecialActionEffect(specialActionId), Duration.OneUse);
        this.usesStack = false;
        this.setRuleVisible(false);
    }

    private YavimayaBloomsageChannelDelayedTriggeredAbility(final YavimayaBloomsageChannelDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public YavimayaBloomsageChannelDelayedTriggeredAbility copy() {
        return new YavimayaBloomsageChannelDelayedTriggeredAbility(this);
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

class YavimayaBloomsageEffect extends OneShotEffect {

    YavimayaBloomsageEffect() {
        super(Outcome.BoostCreature);
        staticText = "put a +1/+1 counter on target creature you control. Then if that creature has power 7 or greater, this creature becomes prepared";
    }

    private YavimayaBloomsageEffect(final YavimayaBloomsageEffect effect) {
        super(effect);
    }

    @Override
    public YavimayaBloomsageEffect copy() {
        return new YavimayaBloomsageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target == null) {
            return false;
        }
        target.addCounters(CounterType.P1P1.createInstance(), source, game);
        game.applyEffects();
        target = game.getPermanent(source.getFirstTarget());
        if (target == null) {
            return true;
        }
        if (target.getPower().getValue() >= 7) {
            Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
            PrepareUtil.prepare(sourcePermanent, game, source);
        }
        return true;
    }
}
