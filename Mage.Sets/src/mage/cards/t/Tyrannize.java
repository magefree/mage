
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
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
 * @author jeffwadsworth using code from LevelX
 */
public final class Tyrannize extends CardImpl {
    
    private static final String rule = "Pay 7 life? (otherwise discard your hand)";

    public Tyrannize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B/R}{B/R}");

        // Target player discards their hand unless they pay 7 life.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new TyrannizeEffect());
    }

    private Tyrannize(final Tyrannize card) {
        super(card);
    }

    @Override
    public Tyrannize copy() {
        return new Tyrannize(this);
    }
}

class TyrannizeEffect extends OneShotEffect {
    
    TyrannizeEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player discards their hand unless they pay 7 life";
    }
    
    private TyrannizeEffect(final TyrannizeEffect effect) {
        super(effect);
    }
    
    @Override
    public TyrannizeEffect copy() {
        return new TyrannizeEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            Cost cost = new PayLifeCost(7);
            if (!cost.canPay(source, source, player.getId(), game)
                    || !player.chooseUse(Outcome.LoseLife, "Pay 7 life?", source, game)
                    || !cost.pay(source, game, source, player.getId(), false, null)) {
                player.discard(player.getHand(), false, source, game);
            }
            return true;
        }
        return false;
    }
}
