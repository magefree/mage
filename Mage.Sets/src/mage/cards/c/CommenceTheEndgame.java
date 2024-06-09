package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CommenceTheEndgame extends CardImpl {

    public CommenceTheEndgame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Draw two cards, then amass X, where X is the number of cards in your hand.
        this.getSpellAbility().addEffect(new CommenceTheEndgameEffect());
    }

    private CommenceTheEndgame(final CommenceTheEndgame card) {
        super(card);
    }

    @Override
    public CommenceTheEndgame copy() {
        return new CommenceTheEndgame(this);
    }
}

class CommenceTheEndgameEffect extends OneShotEffect {

    CommenceTheEndgameEffect() {
        super(Outcome.Benefit);
        staticText = "draw two cards, then amass Zombies X, where X is the number of cards in your hand. " +
                "<i>(Put X +1/+1 counterson an Army you control. It's also a Zombie. If you don't control an Army, " +
                "create a 0/0 black Zombie Army creature token first.)</i>";
    }

    private CommenceTheEndgameEffect(final CommenceTheEndgameEffect effect) {
        super(effect);
    }

    @Override
    public CommenceTheEndgameEffect copy() {
        return new CommenceTheEndgameEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.drawCards(2, source, game);
        AmassEffect.doAmass(player.getHand().size(), SubType.ZOMBIE, game, source);
        return true;
    }
}