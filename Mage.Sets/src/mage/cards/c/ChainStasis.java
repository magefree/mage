
package mage.cards.c;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class ChainStasis extends CardImpl {

    public ChainStasis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // You may tap or untap target creature. Then that creature's controller may pay {2}{U}. If the player does, he or she may copy this spell and may choose a new target for that copy.
        this.getSpellAbility().addEffect(new ChainStasisEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public ChainStasis(final ChainStasis card) {
        super(card);
    }

    @Override
    public ChainStasis copy() {
        return new ChainStasis(this);
    }
}

class ChainStasisEffect extends OneShotEffect {

    public ChainStasisEffect() {
        super(Outcome.Benefit);
    }

    public ChainStasisEffect(final ChainStasisEffect effect) {
        super(effect);
    }

    @Override
    public ChainStasisEffect copy() {
        return new ChainStasisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            Effect effect = new MayTapOrUntapTargetEffect();
            effect.setTargetPointer(new FixedTarget(source.getFirstTarget()));
            effect.apply(game, source);
            Player player = game.getPlayer(permanent.getControllerId());
            Cost cost = new ManaCostsImpl("{2}{U}");
            if (cost.pay(source, game, player.getId(), controller.getId(), false)) {
                if (player.chooseUse(outcome, "Copy the spell?", source, game)) {
                    Spell spell = game.getStack().getSpell(source.getSourceId());
                    if (spell != null) {
                        StackObject newStackObject = spell.createCopyOnStack(game, source, player.getId(), true);
                        if (newStackObject instanceof Spell) {
                            String activateMessage = ((Spell) newStackObject).getActivatedMessage(game);
                            if (activateMessage.startsWith(" casts ")) {
                                activateMessage = activateMessage.substring(6);
                            }
                            game.informPlayers(player.getLogName() + ' ' + activateMessage);
                        }
                    }
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public String getText(Mode mode
    ) {
        return "You may tap or untap target creature. Then that creature's controller may pay {2}{U}. If the player does, he or she may copy this spell and may choose a new target for that copy";
    }

}
