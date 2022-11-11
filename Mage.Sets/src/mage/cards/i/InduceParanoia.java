
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class InduceParanoia extends CardImpl {

    public InduceParanoia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}{U}");
        
        // Counter target spell. If {B} was spent to cast Induce Paranoia, that spell's controller puts the top X cards of their library into their graveyard, where X is the spell's converted mana cost.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new InduceParanoiaEffect(), 
                new CounterTargetEffect(),
                ManaWasSpentCondition.BLACK, "Counter target spell. If {B} was spent to cast this spell, that spell's controller mills X cards, where X is the spell's mana value."));
                
        // Counter target spell. 
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private InduceParanoia(final InduceParanoia card) {
        super(card);
    }

    @Override
    public InduceParanoia copy() {
        return new InduceParanoia(this);
    }
}

class InduceParanoiaEffect extends OneShotEffect {

    InduceParanoiaEffect() {
        super(Outcome.Detriment);
        this.staticText = "Counter target spell. If {B} was spent to cast this spell, that spell's controller mills X cards, where X is the spell's mana value.";
    }

    InduceParanoiaEffect(final InduceParanoiaEffect effect) {
        super(effect);
    }

    @Override
    public InduceParanoiaEffect copy() {
        return new InduceParanoiaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject spell = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (spell != null) { 
            game.getStack().counter(spell.getId(), source, game);
            int spellCMC = spell.getManaValue();
            Player player = game.getPlayer(spell.getControllerId());
            if (player != null) {
                player.millCards(spellCMC, source, game);
                return true;
            }
        }
        return false;
    }
}