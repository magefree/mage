package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DifferentlyNamedPermanentCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GremlinArtifactToken;
import mage.game.permanent.token.Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GimbalGremlinProdigy extends CardImpl {

    public GimbalGremlinProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GREMLIN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Artifact creatures you control have trample.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        )));

        // At the beginning of your end step, create a 0/0 red Gremlin artifact creature token. Put X +1/+1 counters on it, where X is the number of differently named artifact tokens you control.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new GimbalGremlinProdigyEffect())
                .addHint(GimbalGremlinProdigyEffect.getHint()));
    }

    private GimbalGremlinProdigy(final GimbalGremlinProdigy card) {
        super(card);
    }

    @Override
    public GimbalGremlinProdigy copy() {
        return new GimbalGremlinProdigy(this);
    }
}

class GimbalGremlinProdigyEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent("artifact tokens you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    private static final DifferentlyNamedPermanentCount xValue = new DifferentlyNamedPermanentCount(filter);

    static final Hint getHint() {
        return xValue.getHint();
    }

    GimbalGremlinProdigyEffect() {
        super(Outcome.Benefit);
        staticText = "create a 0/0 red Gremlin artifact creature token. Put X +1/+1 counters on it, " +
                "where X is the number of differently named artifact tokens you control";
    }

    private GimbalGremlinProdigyEffect(final GimbalGremlinProdigyEffect effect) {
        super(effect);
    }

    @Override
    public GimbalGremlinProdigyEffect copy() {
        return new GimbalGremlinProdigyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new GremlinArtifactToken();
        token.putOntoBattlefield(1, game, source);
        int amount = xValue.calculate(game, source, this);
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent permanent = game.getPermanent(tokenId);
            if (permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(amount), source, game);
            }
        }
        return true;
    }
}
