package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.ClownRobotToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClowningAround extends CardImpl {

    public ClowningAround(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Create two 1/1 white Clown Robot artifact creature tokens, then roll a six-sided die. If the result is equal to or less than the number of Robots you control, create a 1/1 white Clown Robot artifact creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ClownRobotToken(), 2));
        this.getSpellAbility().addEffect(new ClowningAroundEffect());
    }

    private ClowningAround(final ClowningAround card) {
        super(card);
    }

    @Override
    public ClowningAround copy() {
        return new ClowningAround(this);
    }
}

class ClowningAroundEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ROBOT);

    ClowningAroundEffect() {
        super(Outcome.Benefit);
        staticText = ", then roll a six-sided die. If the result is equal to or less " +
                "than the number of Robots you control, create a 1/1 white Clown Robot artifact creature token";
    }

    private ClowningAroundEffect(final ClowningAroundEffect effect) {
        super(effect);
    }

    @Override
    public ClowningAroundEffect copy() {
        return new ClowningAroundEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int roll = player.rollDice(outcome, source, game, 6);
        if (roll <= game.getBattlefield().count(filter, source.getControllerId(), source, game)) {
            new ClownRobotToken().putOntoBattlefield(1, game, source);
        }
        return true;
    }
}
