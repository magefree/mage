
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.OozeToken;
import mage.game.stack.StackObject;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class MysticGenesis extends CardImpl {

    public MysticGenesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}{U}{U}");

        // Counter target spell. Create an X/X green Ooze creature token, where X is that spell's converted mana cost.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new MysticGenesisEffect());

    }

    private MysticGenesis(final MysticGenesis card) {
        super(card);
    }

    @Override
    public MysticGenesis copy() {
        return new MysticGenesis(this);
    }
}

class MysticGenesisEffect extends OneShotEffect {

    public MysticGenesisEffect() {
        super(Outcome.Detriment);
        staticText = "Counter target spell. Create an X/X green Ooze creature token, where X is that spell's mana value";
    }

    public MysticGenesisEffect(final MysticGenesisEffect effect) {
        super(effect);
    }

    @Override
    public MysticGenesisEffect copy() {
        return new MysticGenesisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (stackObject != null) {
            game.getStack().counter(source.getFirstTarget(), source, game);
            return new CreateTokenEffect(new OozeToken(stackObject.getManaValue(), stackObject.getManaValue())).apply(game, source);
        }
        return false;
    }
}
