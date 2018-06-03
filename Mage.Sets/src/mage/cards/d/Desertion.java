
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author Quercitron
 */
public final class Desertion extends CardImpl {

    public Desertion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");

        // Counter target spell. If an artifact or creature spell is countered this way, put that card onto the battlefield under your control instead of into its owner's graveyard.
        this.getSpellAbility().addEffect(new DesertionEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public Desertion(final Desertion card) {
        super(card);
    }

    @Override
    public Desertion copy() {
        return new Desertion(this);
    }
}

class DesertionEffect extends OneShotEffect {

    public DesertionEffect() {
        super(Outcome.Detriment);
        this.staticText = "Counter target spell. If an artifact or creature spell is countered this way, put that card onto the battlefield under your control instead of into its owner's graveyard";
    }

    public DesertionEffect(final DesertionEffect effect) {
        super(effect);
    }

    @Override
    public DesertionEffect copy() {
        return new DesertionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Spell targetSpell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
            if (targetSpell != null) {
                if (game.getStack().counter(targetSpell.getId(), source.getSourceId(), game)) {
                    game.applyEffects();
                    if (targetSpell.isArtifact() || targetSpell.isCreature()) {
                        Card card = game.getCard(targetSpell.getSourceId());
                        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
