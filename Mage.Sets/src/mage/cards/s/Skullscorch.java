package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author L_J (based on code by dustinconrad)
 */
public final class Skullscorch extends CardImpl {

    public Skullscorch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}{R}");

        // Target player discards two cards at random unless that player has Skullscorch deal 4 damage to them.
        this.getSpellAbility().addEffect(new SkullscorchDiscardEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Skullscorch(final Skullscorch card) {
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
        staticText = "Target player discards two cards at random unless that player has {this} deal 4 damage to them";
    }

    private SkullscorchDiscardEffect(final SkullscorchDiscardEffect effect) {
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
        for (StackObject object : game.getStack()) {
            if (object instanceof Spell && object.getSourceId().equals(source.getSourceId())) {
                spell = object;
            }
        }
        if (spell != null) {
            boolean discardCards = true;
            Player player = game.getPlayer(targetPointer.getFirst(game, source));
            if (player != null) {
                if (player.chooseUse(Outcome.Detriment, "Have " + spell.getLogName() + " deal 4 damage to you?", source, game)) {
                    discardCards = false;
                    player.damage(4, source.getSourceId(), source, game);
                    game.informPlayers(player.getLogName() + " has " + spell.getLogName() + " deal 4 to them");
                }
                if (discardCards) {
                    player.discard(2, true, false, source, game);
                }
            }
            return discardCards;
        }
        return false;
    }

}
