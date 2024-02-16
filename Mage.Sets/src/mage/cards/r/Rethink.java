package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class Rethink extends CardImpl {

    public Rethink(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Counter target spell unless its controller pays {X}, where X is its converted mana cost.
        this.getSpellAbility().addEffect(new RethinkEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private Rethink(final Rethink card) {
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
        this.staticText = "Counter target spell unless its controller pays {X}, where X is its mana value";
    }

    private RethinkEffect(final RethinkEffect effect) {
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
                Cost cost = ManaUtil.createManaCost(spell.getManaValue(), true);
                if (!cost.pay(source, game, source, player.getId(), false)) {
                    game.getStack().counter(spell.getId(), source, game);
                }
                return true;
            }
        }
        return false;
    }
}
