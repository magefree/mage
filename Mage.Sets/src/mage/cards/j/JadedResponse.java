
package mage.cards.j;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;

/**
 *
 * @author TheElk801
 */
public final class JadedResponse extends CardImpl {

    public JadedResponse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target spell if it shares a color with a creature you control.
        this.getSpellAbility().addEffect(new JadedResponseEffect());
    }

    public JadedResponse(final JadedResponse card) {
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

    JadedResponseEffect(final JadedResponseEffect effect) {
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
        ObjectColor creatureColors = new ObjectColor();
        for (Permanent creature : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_CONTROLLED_CREATURES, source.getControllerId(), game)) {
            creatureColors = creatureColors.union(creature.getColor(game));
            if (!creatureColors.intersection(stackObject.getColor(game)).isColorless()) {
                return new CounterTargetEffect().apply(game, source);
            }
        }
        return false;
    }
}
