
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

    public IlluminationEffect() {
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
        UUID targetId = source.getFirstTarget();
        Player controller = null;
        boolean countered = false;
        if (targetId != null) {
            controller = game.getPlayer(game.getControllerId(targetId));
        }
        if (targetId != null
                && game.getStack().counter(targetId, source, game)) {
            countered = true;
        }
        if (controller != null) {
            Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
            int cost = spell.getManaValue();
            Player player = game.getPlayer(spell.getControllerId());
            if (player != null) {
                player.gainLife(cost, game, source);
            }
            return true;
        }
        return countered;
    }
}
