package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.HeroToken;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrahaTiaScionReborn extends CardImpl {

    public GrahaTiaScionReborn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Throw Wide the Gates -- Whenever you cast a noncreature spell, you may pay X life, where X is that spell's mana value. If you do, create a 1/1 colorless Hero creature token and put X +1/+1 counters on it. Do this only once each turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DoIfCostPaid(
                new GrahaTiaScionRebornEffect(),
                new PayLifeCost(
                        GrahaTiaScionRebornValue.instance,
                        "X life, where X is that spell's mana value"
                )
        ), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false).setDoOnlyOnceEachTurn(true).withFlavorWord("Throw Wide the Gates"));
    }

    private GrahaTiaScionReborn(final GrahaTiaScionReborn card) {
        super(card);
    }

    @Override
    public GrahaTiaScionReborn copy() {
        return new GrahaTiaScionReborn(this);
    }
}

enum GrahaTiaScionRebornValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return CardUtil.getEffectValueFromAbility(sourceAbility, "spellCast", Spell.class)
                .map(Spell::getManaValue)
                .orElse(0);
    }

    @Override
    public GrahaTiaScionRebornValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "X";
    }
}

class GrahaTiaScionRebornEffect extends OneShotEffect {

    GrahaTiaScionRebornEffect() {
        super(Outcome.Benefit);
        staticText = "create a 1/1 colorless Hero creature token and put X +1/+1 counters on it";
    }

    private GrahaTiaScionRebornEffect(final GrahaTiaScionRebornEffect effect) {
        super(effect);
    }

    @Override
    public GrahaTiaScionRebornEffect copy() {
        return new GrahaTiaScionRebornEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new HeroToken();
        token.putOntoBattlefield(1, game, source);
        int count = GrahaTiaScionRebornValue.instance.calculate(game, source, this);
        if (count < 1) {
            return true;
        }
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Optional.ofNullable(tokenId)
                    .map(game::getPermanent)
                    .ifPresent(permanent -> permanent.addCounters(CounterType.P1P1.createInstance(count), source, game));
        }
        return true;
    }
}
