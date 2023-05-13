package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.CreateTokenEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.SquirrelToken;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreaturePermanent;

import java.util.Map;
import java.util.UUID;

/**
 * @author weirddan455
 */
public final class ChatterfangSquirrelGeneral extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.SQUIRREL, "Squirrels");

    public ChatterfangSquirrelGeneral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SQUIRREL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Forestwalk
        this.addAbility(new ForestwalkAbility());

        // If one or more tokens would be created under your control, those tokens plus that many 1/1 green Squirrel creature tokens are created instead.
        this.addAbility(new SimpleStaticAbility(new ChatterfangSquirrelGeneralReplacementEffect()));

        // {B}, Sacrifice X Squirrels: Target creature gets +X/-X until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostTargetEffect(
                GetXValue.instance, new SignInversionDynamicValue(GetXValue.instance), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{B}"));
        ability.addCost(new SacrificeXTargetCost(filter));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ChatterfangSquirrelGeneral(final ChatterfangSquirrelGeneral card) {
        super(card);
    }

    @Override
    public ChatterfangSquirrelGeneral copy() {
        return new ChatterfangSquirrelGeneral(this);
    }
}

class ChatterfangSquirrelGeneralReplacementEffect extends ReplacementEffectImpl {

    public ChatterfangSquirrelGeneralReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = "If one or more tokens would be created under your control, those tokens plus that many 1/1 green Squirrel creature tokens are created instead";
    }

    private ChatterfangSquirrelGeneralReplacementEffect(final ChatterfangSquirrelGeneralReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ChatterfangSquirrelGeneralReplacementEffect copy() {
        return new ChatterfangSquirrelGeneralReplacementEffect(this);
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
            SquirrelToken squirrelToken = null;
            int amount = 0;
            Map<Token, Integer> tokens = tokenEvent.getTokens();
            for (Map.Entry<Token, Integer> entry : tokens.entrySet()) {
                amount += entry.getValue();
                if (entry.getKey() instanceof SquirrelToken) {
                    squirrelToken = (SquirrelToken) entry.getKey();
                }
            }
            if (squirrelToken == null) {
                squirrelToken = new SquirrelToken();
            }
            tokens.put(squirrelToken, tokens.getOrDefault(squirrelToken, 0) + amount);
        }
        return false;
    }
}
