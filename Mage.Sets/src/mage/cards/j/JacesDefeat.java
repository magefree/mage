
package mage.cards.j;

import java.util.UUID;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public final class JacesDefeat extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("blue spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public JacesDefeat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target blue spell.  If it was a Jace planeswalker spell, scry 2.
        this.getSpellAbility().addEffect(new JacesDefeatEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private JacesDefeat(final JacesDefeat card) {
        super(card);
    }

    @Override
    public JacesDefeat copy() {
        return new JacesDefeat(this);
    }
}

class JacesDefeatEffect extends OneShotEffect {

    private static final FilterSpell filter = new FilterSpell();

    static {
        filter.add(CardType.PLANESWALKER.getPredicate());
        filter.add(SubType.JACE.getPredicate());
    }

    public JacesDefeatEffect() {
        super(Outcome.Damage);
        this.staticText = "Counter target blue spell. If it was a Jace planeswalker spell, scry 2.";
    }

    public JacesDefeatEffect(final JacesDefeatEffect effect) {
        super(effect);
    }

    @Override
    public JacesDefeatEffect copy() {
        return new JacesDefeatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null) {
            for (UUID targetId : getTargetPointer().getTargets(game, source) ) {
                Spell spell = game.getStack().getSpell(targetId);
                if (spell != null) {
                    game.getStack().counter(targetId, source, game);
                    Player controller = game.getPlayer(source.getControllerId());
                    // If it was a Jace planeswalker, you may discard a card. If you do, draw a card
                    if (filter.match(spell, game) && controller != null) {
                        controller.scry(2, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
