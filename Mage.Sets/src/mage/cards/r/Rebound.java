package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.TargetsOnlyOnePlayerPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.TargetSpell;

/**
 *
 * @author jeffwadsworth
 */
public final class Rebound extends CardImpl {

    public Rebound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Change the target of target spell that targets only a player. The new target must be a player.
        this.getSpellAbility().addEffect(new ReboundEffect());
        FilterSpell filter = new FilterSpell("spell that targets only a player");
        filter.add(new TargetsOnlyOnePlayerPredicate());
        this.getSpellAbility().addTarget(new TargetSpell(filter));

    }

    private Rebound(final Rebound card) {
        super(card);
    }

    @Override
    public Rebound copy() {
        return new Rebound(this);
    }
}

class ReboundEffect extends OneShotEffect {

    public ReboundEffect() {
        super(Outcome.Neutral);
        this.staticText = "Change the target of target spell that targets only a player. The new target must be a player";
    }

    public ReboundEffect(final ReboundEffect effect) {
        super(effect);
    }

    @Override
    public ReboundEffect copy() {
        return new ReboundEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (spell != null
                && controller != null) {
            spell.getSpellAbility().getTargets().clear();
            TargetPlayer targetPlayer = new TargetPlayer();
            if (controller.choose(Outcome.Neutral, targetPlayer, source, game)) {
                spell.getSpellAbility().addTarget(targetPlayer);
                game.informPlayers("The target of the spell was changed to " + targetPlayer.getTargetedName(game));
                return true;
            }
        }
        return false;
    }
}
