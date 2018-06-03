
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author jeffwadsworth
 */
public final class SpellSyphon extends CardImpl {

    public SpellSyphon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");


        // Counter target spell unless its controller pays {1} for each blue permanent you control.
        this.getSpellAbility().addEffect(new SpellSyphonEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        
    }

    public SpellSyphon(final SpellSyphon card) {
        super(card);
    }

    @Override
    public SpellSyphon copy() {
        return new SpellSyphon(this);
    }
}

class SpellSyphonEffect extends OneShotEffect {
    
    private static final FilterPermanent filter = new FilterPermanent("blue permanent you control");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public SpellSyphonEffect() {
        super(Outcome.Detriment);
    }

    public SpellSyphonEffect(final SpellSyphonEffect effect) {
        super(effect);
    }

    @Override
    public SpellSyphonEffect copy() {
        return new SpellSyphonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject spell = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (spell != null) {
            Player player = game.getPlayer(spell.getControllerId());
            Player controller = game.getPlayer(source.getControllerId());
            if (player != null && controller != null) {
                int amount = game.getBattlefield().count(filter, source.getSourceId(), controller.getId(), game);
                if (amount == 0) {
                    game.informPlayers("Spell Syphon: no blue permanents in controller's battlefield.");
                } else {
                    GenericManaCost cost = new GenericManaCost(amount);
                    if (!cost.pay(source, game, spell.getControllerId(), spell.getControllerId(), false)) {
                        game.informPlayers("Spell Syphon: cost wasn't payed - countering target spell.");
                        return game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Counter target spell unless its controller pays {1} for each blue permanent you control";
    }

}
