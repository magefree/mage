package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.ThopterColorlessToken;
import mage.game.stack.StackObject;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AccessDenied extends CardImpl {

    public AccessDenied(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");

        // Counter target spell. Create X 1/1 colorless Thopter artifact creature tokens with flying, where X is that spell's mana value.
        this.getSpellAbility().addEffect(new AccessDeniedEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private AccessDenied(final AccessDenied card) {
        super(card);
    }

    @Override
    public AccessDenied copy() {
        return new AccessDenied(this);
    }
}

class AccessDeniedEffect extends OneShotEffect {

    AccessDeniedEffect() {
        super(Outcome.Benefit);
        staticText = "counter target spell. Create X 1/1 colorless Thopter " +
                "artifact creature tokens with flying, where X is that spell's mana value";
    }

    private AccessDeniedEffect(final AccessDeniedEffect effect) {
        super(effect);
    }

    @Override
    public AccessDeniedEffect copy() {
        return new AccessDeniedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (stackObject != null) {
            game.getStack().counter(source.getFirstTarget(), source, game);
            return new ThopterColorlessToken().putOntoBattlefield(stackObject.getManaValue(), game, source);
        }
        return false;
    }
}
