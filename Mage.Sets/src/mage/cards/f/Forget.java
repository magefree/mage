
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Quercitron
 */
public final class Forget extends CardImpl {

    public Forget(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}{U}");


        // Target player discards two cards, then draws as many cards as they discarded this way.
        this.getSpellAbility().addEffect(new ForgetEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Forget(final Forget card) {
        super(card);
    }

    @Override
    public Forget copy() {
        return new Forget(this);
    }
}

class ForgetEffect extends OneShotEffect {

    public ForgetEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Target player discards two cards, then draws as many cards as they discarded this way";
    }
    
    private ForgetEffect(final ForgetEffect effect) {
        super(effect);
    }
    
    @Override
    public ForgetEffect copy() {
        return new ForgetEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null) {
            targetPlayer.drawCards(targetPlayer.discard(2, false, false, source, game).size(), source, game);
            return true;
        }
        return false;
    }    
}
