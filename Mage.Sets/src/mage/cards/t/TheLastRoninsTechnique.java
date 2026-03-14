package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.condition.common.SneakCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SneakAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.NinjaTurtleSpiritToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheLastRoninsTechnique extends CardImpl {

    public TheLastRoninsTechnique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // Sneak {1}{W}
        this.addAbility(new SneakAbility(this, "{1}{W}"));

        // Create three 1/1 white Ninja Turtle Spirit creature tokens. If this spell's sneak cost was paid, they enter tapped and attacking.
        this.getSpellAbility().addEffect(new TheLastRoninsTechniqueEffect());
    }

    private TheLastRoninsTechnique(final TheLastRoninsTechnique card) {
        super(card);
    }

    @Override
    public TheLastRoninsTechnique copy() {
        return new TheLastRoninsTechnique(this);
    }
}

class TheLastRoninsTechniqueEffect extends OneShotEffect {

    TheLastRoninsTechniqueEffect() {
        super(Outcome.Benefit);
        staticText = "create three 1/1 white Ninja Turtle Spirit creature tokens. " +
                "If this spell's sneak cost was paid, they enter tapped and attacking.";
    }

    private TheLastRoninsTechniqueEffect(final TheLastRoninsTechniqueEffect effect) {
        super(effect);
    }

    @Override
    public TheLastRoninsTechniqueEffect copy() {
        return new TheLastRoninsTechniqueEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean flag = SneakCondition.instance.apply(game, source);
        return new NinjaTurtleSpiritToken().putOntoBattlefield(3, game, source, source.getControllerId(), flag, flag);
    }
}
