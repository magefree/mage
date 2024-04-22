package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.GremlinArtifactToken;
import mage.game.permanent.token.Token;

import java.util.Objects;
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
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new GimbalGremlinProdigyEffect(), TargetController.YOU, false
        ));
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
        int amount = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT,
                        source.getControllerId(), source, game
                )
                .stream()
                .filter(PermanentToken.class::isInstance)
                .map(MageObject::getName)
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .distinct()
                .mapToInt(i -> 1)
                .sum();
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent permanent = game.getPermanent(tokenId);
            if (permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(amount), source, game);
            }
        }
        return true;
    }
}
