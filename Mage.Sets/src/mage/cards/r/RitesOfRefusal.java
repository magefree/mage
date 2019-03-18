
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author jeffwadsworth
 */
public final class RitesOfRefusal extends CardImpl {

    public RitesOfRefusal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Discard any number of cards. Counter target spell unless its controller pays {3} for each card discarded this way.
        this.getSpellAbility().addEffect(new RitesOfRefusalEffect());
        this.getSpellAbility().addTarget(new TargetSpell());

    }

    public RitesOfRefusal(final RitesOfRefusal card) {
        super(card);
    }

    @Override
    public RitesOfRefusal copy() {
        return new RitesOfRefusal(this);
    }
}

class RitesOfRefusalEffect extends OneShotEffect {

    RitesOfRefusalEffect() {
        super(Outcome.AIDontUseIt);
        this.staticText = "Discard any number of cards. Counter target spell unless its controller pays {3} for each card discarded this way";
    }

    RitesOfRefusalEffect(final RitesOfRefusalEffect effect) {
        super(effect);
    }

    @Override
    public RitesOfRefusalEffect copy() {
        return new RitesOfRefusalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell targetSpell = game.getStack().getSpell(source.getFirstTarget());
        if (targetSpell != null) {
            Player controllerOfTargetedSpell = game.getPlayer(targetSpell.getControllerId());
            if (controller != null
                    && controllerOfTargetedSpell != null) {
                int numToDiscard = controller.getAmount(0, controller.getHand().size(), "How many cards do you want to discard?", game);
                Cards discardedCards = controller.discard(numToDiscard, false, source, game);
                int actualNumberDiscarded = discardedCards.size();
                GenericManaCost cost = new GenericManaCost(actualNumberDiscarded * 3);
                if (controllerOfTargetedSpell.chooseUse(Outcome.AIDontUseIt, "Do you want to pay " + cost.convertedManaCost() + " to prevent " + targetSpell.getName() + " from gettting countered?", source, game)
                        && cost.pay(source, game, source.getSourceId(), controllerOfTargetedSpell.getId(), false)) {
                    return true;
                } else {
                    targetSpell.counter(source.getSourceId(), game);
                    return true;
                }
            }
        }
        return false;
    }
}
