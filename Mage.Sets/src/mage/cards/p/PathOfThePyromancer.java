package mage.cards.p;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.WillOfThePlaneswalkersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PathOfThePyromancer extends CardImpl {

    public PathOfThePyromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Discard all the cards in your hand. Add {R} for each card discarded this way, then draw that many cards plus one.
        this.getSpellAbility().addEffect(new PathOfThePyromancerEffect());

        // Will of the Planeswalkers -- Starting with you, each player votes for planeswalk or chaos. If planeswalk gets more votes, planeswalk. If chaos gets more votes or the vote is tied, chaos ensues.
        this.getSpellAbility().addEffect(new WillOfThePlaneswalkersEffect());
    }

    private PathOfThePyromancer(final PathOfThePyromancer card) {
        super(card);
    }

    @Override
    public PathOfThePyromancer copy() {
        return new PathOfThePyromancer(this);
    }
}

class PathOfThePyromancerEffect extends OneShotEffect {

    PathOfThePyromancerEffect() {
        super(Outcome.Benefit);
        staticText = "discard all the cards in your hand. Add {R} " +
                "for each card discarded this way, then draw that many cards plus one";
    }

    private PathOfThePyromancerEffect(final PathOfThePyromancerEffect effect) {
        super(effect);
    }

    @Override
    public PathOfThePyromancerEffect copy() {
        return new PathOfThePyromancerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int discarded = player.discard(player.getHand(), false, source, game).size();
        player.getManaPool().addMana(Mana.RedMana(discarded), game, source);
        player.drawCards(discarded + 1, source, game);
        return true;
    }
}
