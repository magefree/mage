
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseNewTargetsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterSpell;
import mage.filter.predicate.other.NumberOfTargetsPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class ImpsMischief extends CardImpl {
    
    private static final FilterSpell filter = new FilterSpell("spell with a single target");
    
    static {
        filter.add(new NumberOfTargetsPredicate(1));
    }
    
    public ImpsMischief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}");


        // Change the target of target spell with a single target. You lose life equal to that spell's converted mana cost.
        this.getSpellAbility().addEffect(new ChooseNewTargetsTargetEffect(true, true));
        this.getSpellAbility().addEffect(new ImpsMischiefLoseLifeEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        
    }

    private ImpsMischief(final ImpsMischief card) {
        super(card);
    }

    @Override
    public ImpsMischief copy() {
        return new ImpsMischief(this);
    }
}

class ImpsMischiefLoseLifeEffect extends OneShotEffect {


    public ImpsMischiefLoseLifeEffect() {
        super(Outcome.LoseLife);
        staticText = "You lose life equal to that spell's mana value";
    }

    private ImpsMischiefLoseLifeEffect(final ImpsMischiefLoseLifeEffect effect) {
        super(effect);
    }

    @Override
    public ImpsMischiefLoseLifeEffect copy() {
        return new ImpsMischiefLoseLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(source.getFirstTarget());
        if (spell != null) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.loseLife(spell.getManaValue(), game, source, false);
                return true;
            }            
        }        
        return false;
    }

}
