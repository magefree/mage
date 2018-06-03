
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.game.stack.StackObject;
import mage.target.TargetSpell;

/**
 *
 * @author TheElk801
 */
public final class SpellSwindle extends CardImpl {

    public SpellSwindle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");

        // Counter target spell. Create X colorless Treasure artifact tokens, where X is that spell's converted mana cost. They have "T, Sacrifice this artifact: Add one mana of any color."
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new SpellSwindleEffect());
    }

    public SpellSwindle(final SpellSwindle card) {
        super(card);
    }

    @Override
    public SpellSwindle copy() {
        return new SpellSwindle(this);
    }
}

class SpellSwindleEffect extends OneShotEffect {

    public SpellSwindleEffect() {
        super(Outcome.Detriment);
        staticText = "Counter target spell. Create X colorless Treasure artifact tokens, where X is that spell's converted mana cost. "
                + "They have \"{T}, Sacrifice this artifact: Add one mana of any color.\"";
    }

    public SpellSwindleEffect(final SpellSwindleEffect effect) {
        super(effect);
    }

    @Override
    public SpellSwindleEffect copy() {
        return new SpellSwindleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (stackObject != null) {
            game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game);
            return new CreateTokenEffect(new TreasureToken(), stackObject.getConvertedManaCost()).apply(game, source);
        }
        return false;
    }
}
