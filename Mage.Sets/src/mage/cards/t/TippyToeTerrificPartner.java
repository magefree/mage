package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.CreateTokenEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.FoodToken;
import mage.util.CardUtil;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class TippyToeTerrificPartner extends CardImpl {

    private static final Condition condition = YouGainedLifeCondition.getZero();

    public TippyToeTerrificPartner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SQUIRREL);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // If you would create one or more tokens, instead create those tokens plus an additional Food token.
        this.addAbility(new SimpleStaticAbility(new TippyToeTerrificPartnerEffect()));

        // At the beginning of your end step, if you gained life this turn, draw a card.
        Ability triggeredAbility = new BeginningOfEndStepTriggeredAbility(new DrawCardSourceControllerEffect(1)).withInterveningIf(condition);
        this.addAbility(triggeredAbility.addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());
    }

    private TippyToeTerrificPartner(final TippyToeTerrificPartner card) {
        super(card);
    }

    @Override
    public TippyToeTerrificPartner copy() {
        return new TippyToeTerrificPartner(this);
    }
}

class TippyToeTerrificPartnerEffect extends ReplacementEffectImpl {

    TippyToeTerrificPartnerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = "if you would create one or more tokens, instead create those tokens plus an additional Food token";
    }

    private TippyToeTerrificPartnerEffect(final TippyToeTerrificPartnerEffect effect) {
        super(effect);
    }

    @Override
    public TippyToeTerrificPartnerEffect copy() {
        return new TippyToeTerrificPartnerEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATE_TOKEN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (!(event instanceof CreateTokenEvent)) {
            return false;
        }
        CreateTokenEvent tokenEvent = (CreateTokenEvent) event;
        FoodToken foodToken = CardUtil
            .castStream(tokenEvent.getTokens().keySet().stream(), FoodToken.class)
            .findFirst()
            .orElseGet(FoodToken::new);
        tokenEvent
            .getTokens()
            .compute(foodToken, CardUtil::setOrIncrementValue);
        return false;
    }
}
