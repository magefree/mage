package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JadedResponse extends CardImpl {

    public JadedResponse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target spell if it shares a color with a creature you control.
        this.getSpellAbility().addEffect(new JadedResponseEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private JadedResponse(final JadedResponse card) {
        super(card);
    }

    @Override
    public JadedResponse copy() {
        return new JadedResponse(this);
    }
}

class JadedResponseEffect extends OneShotEffect {

    JadedResponseEffect() {
        super(Outcome.Benefit);
        this.staticText = "Counter target spell if it shares a color with a creature you control";
    }

    private JadedResponseEffect(final JadedResponseEffect effect) {
        super(effect);
    }

    @Override
    public JadedResponseEffect copy() {
        return new JadedResponseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(source.getFirstTarget());
        if (stackObject == null) {
            return false;
        }
        boolean matches = game.getBattlefield()
                .getAllActivePermanents(
                        StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game
                ).stream()
                .map(permanent -> permanent.getColor(game))
                .anyMatch(stackObject.getColor(game)::shares);
        return matches && game.getStack().counter(stackObject.getId(), source, game);
    }
}
