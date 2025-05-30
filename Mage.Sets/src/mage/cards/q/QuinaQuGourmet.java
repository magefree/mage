package mage.cards.q;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.CreateTokenEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.FrogGreenToken;
import mage.game.permanent.token.Token;
import mage.util.CardUtil;

import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QuinaQuGourmet extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.FROG, "Frog");

    public QuinaQuGourmet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.QU);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // If one or more tokens would be created under your control, those tokens plus a 1/1 green Frog creature token are created instead.
        this.addAbility(new SimpleStaticAbility(new QuinaQuGourmetEffect()));

        // {2}, Sacrifice a Frog: Put a +1/+1 counter on Quina.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new GenericManaCost(2)
        );
        ability.addCost(new SacrificeTargetCost(filter));
        this.addAbility(ability);
    }

    private QuinaQuGourmet(final QuinaQuGourmet card) {
        super(card);
    }

    @Override
    public QuinaQuGourmet copy() {
        return new QuinaQuGourmet(this);
    }
}

class QuinaQuGourmetEffect extends ReplacementEffectImpl {

    QuinaQuGourmetEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if one or more tokens would be created under your control, " +
                "those tokens plus a 1/1 green Frog creature token are created instead";
    }

    private QuinaQuGourmetEffect(final QuinaQuGourmetEffect effect) {
        super(effect);
    }

    @Override
    public QuinaQuGourmetEffect copy() {
        return new QuinaQuGourmetEffect(this);
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
        Map<Token, Integer> tokens = ((CreateTokenEvent) event).getTokens();
        Token token = CardUtil
                .castStream(tokens.values(), FrogGreenToken.class)
                .findAny()
                .orElseGet(FrogGreenToken::new);
        tokens.compute(token, CardUtil::setOrIncrementValue);
        return false;
    }
}
