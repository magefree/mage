
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.GiantWarriorToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class FeudkillersVerdict extends CardImpl {

    public FeudkillersVerdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.SORCERY}, "{4}{W}{W}");
        this.subtype.add(SubType.GIANT);

        // You gain 10 life. Then if you have more life than an opponent, create a 5/5 white Giant Warrior creature token.
        this.getSpellAbility().addEffect(new FeudkillersVerdictEffect());
    }

    private FeudkillersVerdict(final FeudkillersVerdict card) {
        super(card);
    }

    @Override
    public FeudkillersVerdict copy() {
        return new FeudkillersVerdict(this);
    }
}

class FeudkillersVerdictEffect extends OneShotEffect {

    public FeudkillersVerdictEffect() {
        super(Outcome.Benefit);
        this.staticText = "You gain 10 life. Then if you have more life than an opponent, create a 5/5 white Giant Warrior creature token";
    }

    private FeudkillersVerdictEffect(final FeudkillersVerdictEffect effect) {
        super(effect);
    }

    @Override
    public FeudkillersVerdictEffect copy() {
        return new FeudkillersVerdictEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.gainLife(10, game, source);
            boolean moreLife = false;
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    if (controller.getLife() > opponent.getLife()) {
                        moreLife = true;
                        break;
                    }
                }

            }
            if (moreLife) {
                return new CreateTokenEffect(new GiantWarriorToken(), 1).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
