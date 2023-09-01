

package mage.cards.u;

import java.util.UUID;
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

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class UnifiedWill extends CardImpl {

    public UnifiedWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Counter target spell if you control more creatures than that spell's controller.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new UnifiedWillEffect());
    }

    private UnifiedWill(final UnifiedWill card) {
        super(card);
    }

    @Override
    public UnifiedWill copy() {
        return new UnifiedWill(this);
    }

}

class UnifiedWillEffect extends OneShotEffect {

    public UnifiedWillEffect() {
        super(Outcome.Detriment);
        staticText = "Counter target spell if you control more creatures than that spell's controller";
    }

    private UnifiedWillEffect(final UnifiedWillEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(source.getFirstTarget());
        if (stackObject != null) {
            if (game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game) > game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, stackObject.getControllerId(), game)) {
                return game.getStack().counter(source.getFirstTarget(), source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public UnifiedWillEffect copy() {
        return new UnifiedWillEffect(this);
    }

}