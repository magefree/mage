
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author L_J (based on code by dustinconrad)
 */
public final class Skullscorch extends CardImpl {

    public Skullscorch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}{R}");

        // Target player discards two cards at random unless that player has Skullscorch deal 4 damage to him or her.
        this.getSpellAbility().addEffect(new SkullscorchDiscardEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public Skullscorch(final Skullscorch card) {
        super(card);
    }

    @Override
    public Skullscorch copy() {
        return new Skullscorch(this);
    }
}

class SkullscorchDiscardEffect extends OneShotEffect {

    public SkullscorchDiscardEffect() {
        super(Outcome.DrawCard);
        staticText = "Target player discards two cards at random unless that player has {source} deal 4 damage to him or her";
    }

    public SkullscorchDiscardEffect(final SkullscorchDiscardEffect effect) {
        super(effect);
    }

    @Override
    public SkullscorchDiscardEffect copy() {
        return new SkullscorchDiscardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        StackObject spell = null;
        for(StackObject object : game.getStack()){
            if(object instanceof Spell && object.getSourceId().equals(source.getSourceId())){
                spell = object;
            }
        }
        if(spell != null){
            boolean discardCards = true;
            Player player = game.getPlayer(targetPointer.getFirst(game, source));
            if (player != null) { 
                if (player.chooseUse(Outcome.Detriment, "Have " + spell.getLogName() + " deal 4 damage to you?", source, game)){
                    discardCards = false;
                    player.damage(4, source.getSourceId(), game, false, true);
                    game.informPlayers(player.getLogName() + " has " + spell.getLogName() + " deal 4 to him or her");
                }
                if (discardCards) {
                    player.discard(2, true, source, game);
                }
            }
            return discardCards;
        }
        return false;
    }

}
