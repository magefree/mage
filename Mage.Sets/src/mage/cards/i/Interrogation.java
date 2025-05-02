
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Styxo
 */
public final class Interrogation extends CardImpl {

    public Interrogation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");

        // Target player discards a card. Then that player discards another card unless they pay 3 life.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DiscardTargetEffect(1));
        this.getSpellAbility().addEffect(new InterrogationEffect());
    }

    private Interrogation(final Interrogation card) {
        super(card);
    }

    @Override
    public Interrogation copy() {
        return new Interrogation(this);
    }
}

class InterrogationEffect extends OneShotEffect {

    InterrogationEffect() {
        super(Outcome.Discard);
        this.staticText = "Then that player discards another card unless they pay 3 life";
    }

    private InterrogationEffect(final InterrogationEffect effect) {
        super(effect);
    }

    @Override
    public InterrogationEffect copy() {
        return new InterrogationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            Cost cost = new PayLifeCost(3);
            if (!cost.canPay(source, source, player.getId(), game)
                    || !player.chooseUse(Outcome.LoseLife, "Pay 3 life?", source, game)
                    || !cost.pay(source, game, source, player.getId(), false, null)) {
                player.discardOne(false, false, source, game);
            }
            return true;
        }
        return false;
    }
}
