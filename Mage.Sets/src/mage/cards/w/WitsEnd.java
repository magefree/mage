package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author North
 */
public final class WitsEnd extends CardImpl {

    public WitsEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}{B}");

        // Target player discards their hand.
        this.getSpellAbility().addEffect(new WitsEndEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private WitsEnd(final WitsEnd card) {
        super(card);
    }

    @Override
    public WitsEnd copy() {
        return new WitsEnd(this);
    }
}

class WitsEndEffect extends OneShotEffect {

    WitsEndEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target player discards their hand";
    }

    private WitsEndEffect(final WitsEndEffect effect) {
        super(effect);
    }

    @Override
    public WitsEndEffect copy() {
        return new WitsEndEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        player.discard(player.getHand(), false, source, game);
        return true;
    }
}
