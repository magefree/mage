
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author Styxo
 */
public final class ParallectricFeedback extends CardImpl {

    public ParallectricFeedback(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Parallectric Feedback deals damage to target spell's controller equal to that spell's converted mana cost.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new ParallectricFeedbackEffect());

    }

    public ParallectricFeedback(final ParallectricFeedback card) {
        super(card);
    }

    @Override
    public ParallectricFeedback copy() {
        return new ParallectricFeedback(this);
    }
}

class ParallectricFeedbackEffect extends OneShotEffect {

    public ParallectricFeedbackEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals damage to target spell's controller equal to that spell's converted mana cost";
    }

    public ParallectricFeedbackEffect(final ParallectricFeedbackEffect effect) {
        super(effect);
    }

    @Override
    public ParallectricFeedbackEffect copy() {
        return new ParallectricFeedbackEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Spell spell = game.getStack().getSpell(source.getFirstTarget());
            if (spell != null) {
                Player spellController = game.getPlayer(spell.getControllerId());
                if (spellController != null) {
                    spellController.damage(spell.getConvertedManaCost(), source.getSourceId(), game, false, true);
                    return true;
                }
            }
        }
        return false;
    }
}
