
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author markedagain
 */
public final class Apocalypse extends CardImpl {

    public Apocalypse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}{R}{R}");

        // Exile all permanents. You discard your hand.
        this.getSpellAbility().addEffect(new ApocalypseExileAllPermanentsEffect());
        this.getSpellAbility().addEffect(new ApocalypseDiscardEffect());
    }

    public Apocalypse(final Apocalypse card) {
        super(card);
    }

    @Override
    public Apocalypse copy() {
        return new Apocalypse(this);
    }
}
class ApocalypseDiscardEffect extends OneShotEffect {

    public ApocalypseDiscardEffect() {
        super(Outcome.Discard);
        this.staticText = "Discard your hand";
    }

    public ApocalypseDiscardEffect(final ApocalypseDiscardEffect effect) {
        super(effect);
    }

    @Override
    public ApocalypseDiscardEffect copy() {
        return new ApocalypseDiscardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (Card card : player.getHand().getCards(game)) {
                player.discard(card, source, game);
            }
            return true;
        }
        return false;
    }
}
class ApocalypseExileAllPermanentsEffect extends OneShotEffect {

    public ApocalypseExileAllPermanentsEffect() {
        super(Outcome.Exile);
        staticText = "Exile all permanents";
    }

    public ApocalypseExileAllPermanentsEffect(final ApocalypseExileAllPermanentsEffect effect) {
        super(effect);
    }

    @Override
    public ApocalypseExileAllPermanentsEffect copy() {
        return new ApocalypseExileAllPermanentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
                permanent.moveToExile(null, null, source.getSourceId(), game);
        }
        return true;
    }
}