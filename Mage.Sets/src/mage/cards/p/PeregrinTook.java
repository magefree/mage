package mage.cards.p;

import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.CreateTokenEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.Token;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Grath
 */
public final class PeregrinTook extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.FOOD, "Foods");

    public PeregrinTook(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // If one or more tokens would be created under your control, those tokens plus an additional Food token are created instead.
        this.addAbility(new SimpleStaticAbility(new PeregrinTookReplacementEffect()));

        // Sacrifice three Foods: Draw a card.
        this.addAbility(new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1),
                new SacrificeTargetCost(new TargetControlledPermanent(3, filter))
        ));
    }

    private PeregrinTook(final PeregrinTook card) {
        super(card);
    }

    @Override
    public PeregrinTook copy() {
        return new PeregrinTook(this);
    }
}

class PeregrinTookReplacementEffect extends ReplacementEffectImpl {

    public PeregrinTookReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = "If one or more tokens would be created under your control, those tokens plus an additional Food token are created instead";
    }

    private PeregrinTookReplacementEffect(final PeregrinTookReplacementEffect effect) {
        super(effect);
    }

    @Override
    public PeregrinTookReplacementEffect copy() {
        return new PeregrinTookReplacementEffect(this);
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
        if (event instanceof CreateTokenEvent) {
            CreateTokenEvent tokenEvent = (CreateTokenEvent) event;
            FoodToken foodToken = null;
            Map<Token, Integer> tokens = tokenEvent.getTokens();
            for (Map.Entry<Token, Integer> entry : tokens.entrySet()) {
                if (entry.getKey() instanceof FoodToken) {
                    foodToken = (FoodToken) entry.getKey();
                }
            }
            if (foodToken == null) {
                foodToken = new FoodToken();
            }
            tokens.put(foodToken, tokens.getOrDefault(foodToken, 0) + 1);
        }
        return false;
    }
}