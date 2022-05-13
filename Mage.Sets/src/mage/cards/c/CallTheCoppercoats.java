package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.abilityword.StriveAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterOpponent;
import mage.filter.FilterPlayer;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.HumanSoldierToken;
import mage.game.permanent.token.Token;
import mage.target.Target;
import mage.target.TargetPlayer;

import java.util.Collection;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CallTheCoppercoats extends CardImpl {

    private static final FilterPlayer filter = new FilterOpponent();

    public CallTheCoppercoats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Strive — This spell costs {1}{W} more to cast for each target beyond the first.
        this.addAbility(new StriveAbility("{1}{W}"));

        // Choose any number of target opponents. Create X 1/1 white Human soldier creature tokens, where X is the number of creatures those opponents control.
        this.getSpellAbility().addEffect(new CallTheCoppercoatsEffect());
        this.getSpellAbility().addTarget(new TargetPlayer(0, Integer.MAX_VALUE, false, filter));
    }

    private CallTheCoppercoats(final CallTheCoppercoats card) {
        super(card);
    }

    @Override
    public CallTheCoppercoats copy() {
        return new CallTheCoppercoats(this);
    }
}

class CallTheCoppercoatsEffect extends OneShotEffect {

    private static final Token token = new HumanSoldierToken();

    CallTheCoppercoatsEffect() {
        super(Outcome.Benefit);
        staticText = "Choose any number of target opponents. Create X 1/1 white Human Soldier creature tokens, " +
                "where X is the number of creatures those opponents control.";
    }

    private CallTheCoppercoatsEffect(final CallTheCoppercoatsEffect effect) {
        super(effect);
    }

    @Override
    public CallTheCoppercoatsEffect copy() {
        return new CallTheCoppercoatsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return token.putOntoBattlefield(
                source.getTargets()
                        .stream()
                        .map(Target::getTargets)
                        .flatMap(Collection::stream)
                        .mapToInt(uuid -> game.getBattlefield().countAll(
                                StaticFilters.FILTER_PERMANENT_CREATURE, uuid, game
                        )).sum(),
                game, source, source.getControllerId()
        );
    }
}
