
package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class TheBattleOfNaboo extends CardImpl {

    public TheBattleOfNaboo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{U}{U}");

        // Return X target creatures to their owner's hands. Draw twice that many cards.
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("Return X target creatures to their owner's hands");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new TheBattleOfNabooEffect());
        this.getSpellAbility().setTargetAdjuster(TheBattleOfNabooAdjuster.instance);
    }

    private TheBattleOfNaboo(final TheBattleOfNaboo card) {
        super(card);
    }

    @Override
    public TheBattleOfNaboo copy() {
        return new TheBattleOfNaboo(this);
    }
}

enum TheBattleOfNabooAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetCreaturePermanent(ability.getManaCostsToPay().getX()));
    }
}

class TheBattleOfNabooEffect extends OneShotEffect {

    public TheBattleOfNabooEffect() {
        super(Outcome.DrawCard);
        staticText = "Draw twice that many cards";
    }

    private TheBattleOfNabooEffect(final TheBattleOfNabooEffect effect) {
        super(effect);
    }

    @Override
    public TheBattleOfNabooEffect copy() {
        return new TheBattleOfNabooEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int x = source.getManaCostsToPay().getX();
            if (x > 0) {
                player.drawCards(2 * x, source, game);
            }
            return true;
        }
        return false;
    }
}
