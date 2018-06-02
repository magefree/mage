
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public final class Rethink extends CardImpl {

    public Rethink(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");

        // Counter target spell unless its controller pays {X}, where X is its converted mana cost.
        this.getSpellAbility().addEffect(new RethinkEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public Rethink(final Rethink card) {
        super(card);
    }

    @Override
    public Rethink copy() {
        return new Rethink(this);
    }
}

class RethinkEffect extends OneShotEffect {

    RethinkEffect() {
        super(Outcome.Detriment);
        this.staticText = "Counter target spell unless that player pays {X}, where X is its converted mana cost";
    }

    RethinkEffect(final RethinkEffect effect) {
        super(effect);
    }

    @Override
    public RethinkEffect copy() {
        return new RethinkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject spell = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (spell != null) {
            Player player = game.getPlayer(spell.getControllerId());
            if (player != null) {
                GenericManaCost cost = new GenericManaCost(spell.getConvertedManaCost());
                if (!cost.pay(source, game, source.getSourceId(), player.getId(), false)) {
                    game.getStack().counter(spell.getId(), source.getSourceId(), game);
                }
                return true;
            }
        }
        return false;
    }
}
