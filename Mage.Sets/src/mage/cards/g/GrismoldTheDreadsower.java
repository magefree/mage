package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.token.GrismoldPlantToken;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrismoldTheDreadsower extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a creature token");

    static {
        filter.add(TokenPredicate.instance);
    }

    public GrismoldTheDreadsower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your end step, each player creates a 1/1 green Plant creature token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new GrismoldTheDreadsowerEffect(), TargetController.YOU, false
        ));

        // Whenever a creature token dies, put a +1/+1 counter on Grismold, the Dreadsower.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, filter
        ));
    }

    private GrismoldTheDreadsower(final GrismoldTheDreadsower card) {
        super(card);
    }

    @Override
    public GrismoldTheDreadsower copy() {
        return new GrismoldTheDreadsower(this);
    }
}

class GrismoldTheDreadsowerEffect extends OneShotEffect {

    GrismoldTheDreadsowerEffect() {
        super(Outcome.Benefit);
        staticText = "each player creates a 1/1 green Plant creature token";
    }

    private GrismoldTheDreadsowerEffect(final GrismoldTheDreadsowerEffect effect) {
        super(effect);
    }

    @Override
    public GrismoldTheDreadsowerEffect copy() {
        return new GrismoldTheDreadsowerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getState().getPlayersInRange(source.getControllerId(), game).stream().forEach(playerId -> {
            Effect effect = new CreateTokenTargetEffect(new GrismoldPlantToken(), 1);
            effect.setTargetPointer(new FixedTarget(playerId, game));
            effect.apply(game, source);
        });
        return true;
    }
}
