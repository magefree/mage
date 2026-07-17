package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.AllyToken;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IrohTeaMaster extends CardImpl {

    public IrohTeaMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Iroh enters, create a Food token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken())));

        // At the beginning of combat on your turn, you may have target opponent gain control of target permanent you control. When you do, create a 1/1 white Ally creature token. Put a +1/+1 counter on that token for each permanent you own that your opponents control.
        Ability ability = new BeginningOfCombatTriggeredAbility(new IrohTeaMasterControlEffect(), true);
        ability.addTarget(new TargetOpponent().setTargetTag(1));
        ability.addTarget(new TargetControlledPermanent().setTargetTag(2));
        this.addAbility(ability.addHint(IrohTeaMasterTokenEffect.getHint()));
    }

    private IrohTeaMaster(final IrohTeaMaster card) {
        super(card);
    }

    @Override
    public IrohTeaMaster copy() {
        return new IrohTeaMaster(this);
    }
}

class IrohTeaMasterControlEffect extends OneShotEffect {

    IrohTeaMasterControlEffect() {
        super(Outcome.Benefit);
        staticText = "have target opponent gain control of target permanent you control. " +
                "When you do, create a 1/1 white Ally creature token. " +
                "Put a +1/+1 counter on that token for each permanent you own that your opponents control";
    }

    private IrohTeaMasterControlEffect(final IrohTeaMasterControlEffect effect) {
        super(effect);
    }

    @Override
    public IrohTeaMasterControlEffect copy() {
        return new IrohTeaMasterControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getTargets().getByTag(1).getFirstTarget());
        Permanent permanent = game.getPermanent(source.getTargets().getByTag(2).getFirstTarget());
        if (player == null || permanent == null) {
            return false;
        }
        game.addEffect(new GainControlTargetEffect(Duration.Custom, true, player.getId())
                .setTargetPointer(new FixedTarget(permanent, game)), source);
        game.fireReflexiveTriggeredAbility(new ReflexiveTriggeredAbility(new IrohTeaMasterTokenEffect(), false), source);
        return true;
    }
}

class IrohTeaMasterTokenEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    private static final Hint hint = new ValueHint(
            "Permanents you own that your opponents control", new PermanentsOnBattlefieldCount(filter)
    );

    public static Hint getHint() {
        return hint;
    }

    IrohTeaMasterTokenEffect() {
        super(Outcome.Benefit);
        staticText = "create a 1/1 white Ally creature token. Put a +1/+1 counter " +
                "on that token for each permanent you own that your opponents control";
    }

    private IrohTeaMasterTokenEffect(final IrohTeaMasterTokenEffect effect) {
        super(effect);
    }

    @Override
    public IrohTeaMasterTokenEffect copy() {
        return new IrohTeaMasterTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new AllyToken();
        token.putOntoBattlefield(1, game, source);
        int count = game.getBattlefield().count(filter, source.getControllerId(), source, game);
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
