
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author cbt33
 */
public final class Mudhole extends CardImpl {

    public Mudhole(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");


        // Target player exiles all land cards from their graveyard.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new MudholeEffect());
    }

    public Mudhole(final Mudhole card) {
        super(card);
    }

    @Override
    public Mudhole copy() {
        return new Mudhole(this);
    }
}

class MudholeEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterLandCard();

    public MudholeEffect() {
        super(Outcome.Exile);
        staticText = "Target player exiles all land cards from their graveyard";
    }

    public MudholeEffect(final MudholeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            for (Card card : targetPlayer.getGraveyard().getCards(filter, game)) {
                card.moveToExile(null, "", source.getSourceId(), game);
            }
            return true;
        }
        return false;
    }

    @Override
    public MudholeEffect copy() {
        return new MudholeEffect(this);
    }
}
