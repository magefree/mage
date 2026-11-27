
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public final class Illumination extends CardImpl {
    
    private static final FilterSpell filter = new FilterSpell("artifact or enchantment spell");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()));
    }

    public Illumination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}{W}");

        // Counter target artifact or enchantment spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        
        // Its controller gains life equal to its converted mana cost.
        this.getSpellAbility().addEffect(new IlluminationEffect());
    }

    private Illumination(final Illumination card) {
        super(card);
    }

    @Override
    public Illumination copy() {
        return new Illumination(this);
    }
}

class IlluminationEffect extends OneShotEffect {

    IlluminationEffect() {
        super(Outcome.GainLife);
        staticText = "Its controller gains life equal to its mana value";
    }

    private IlluminationEffect(final IlluminationEffect effect) {
        super(effect);
    }

    @Override
    public IlluminationEffect copy() {
        return new IlluminationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // CounterTargetEffect already countered the spell, so it is no longer on the stack.
        // CR 608.2h: use its last known information for the mana value and the controller.
        Spell spell = game.getSpellOrLKIStack(getTargetPointer().getFirst(game, source));
        if (spell == null) {
            return false;
        }
        Player player = game.getPlayer(spell.getControllerId());
        if (player == null) {
            return false;
        }
        player.gainLife(spell.getManaValue(), game, source);
        return true;
    }
}
