
package mage.cards.a;

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
 * @author emerald000
 */
public final class Aethersnatch extends CardImpl {

    public Aethersnatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{U}{U}");

        // Gain control of target spell. You may choose new targets for it.
        this.getSpellAbility().addEffect(new AethersnatchEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private Aethersnatch(final Aethersnatch card) {
        super(card);
    }

    @Override
    public Aethersnatch copy() {
        return new Aethersnatch(this);
    }
}

class AethersnatchEffect extends OneShotEffect {
    
    AethersnatchEffect() {
        super(Outcome.GainControl);
        this.staticText = "Gain control of target spell. You may choose new targets for it";
    }
    
    private AethersnatchEffect(final AethersnatchEffect effect) {
        super(effect);
    }
    
    @Override
    public AethersnatchEffect copy() {
        return new AethersnatchEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = game.getStack().getSpell(this.getTargetPointer().getFirst(game, source));
        if (controller != null && spell != null) {
            spell.setControllerId(controller.getId());
            spell.chooseNewTargets(game, controller.getId(), false, false, null);
            return true;
        }
        return false;
    }
}
