
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class ChainOfSmog extends CardImpl {

    public ChainOfSmog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Target player discards two cards. That player may copy this spell and may choose a new target for that copy.
        this.getSpellAbility().addEffect(new ChainOfSmogEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private ChainOfSmog(final ChainOfSmog card) {
        super(card);
    }

    @Override
    public ChainOfSmog copy() {
        return new ChainOfSmog(this);
    }
}

class ChainOfSmogEffect extends OneShotEffect {

    ChainOfSmogEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player discards two cards. That player may copy this spell and may choose a new target for that copy.";
    }

    ChainOfSmogEffect(final ChainOfSmogEffect effect) {
        super(effect);
    }

    @Override
    public ChainOfSmogEffect copy() {
        return new ChainOfSmogEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID targetId = source.getFirstTarget();
            Player affectedPlayer = game.getPlayer(targetId);
            if (affectedPlayer != null) {
                Effect effect = new DiscardTargetEffect(2);
                effect.setTargetPointer(new FixedTarget(targetId, game));
                effect.apply(game, source);
                if (affectedPlayer.chooseUse(Outcome.Copy, "Copy the spell?", source, game)) {
                    Spell spell = game.getStack().getSpell(source.getSourceId());
                    if (spell != null) {
                        spell.createCopyOnStack(game, source, affectedPlayer.getId(), true);
                        game.informPlayers(affectedPlayer.getLogName() + " copies " + spell.getName() + '.');
                    }
                }
                return true;
            }
        }
        return false;
    }
}
