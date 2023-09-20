
package mage.cards.l;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

/**
 *
 * @author North
 */
public final class LifesFinale extends CardImpl {

    public LifesFinale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}{B}");


        this.getSpellAbility().addEffect(new LifesFinaleEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private LifesFinale(final LifesFinale card) {
        super(card);
    }

    @Override
    public LifesFinale copy() {
        return new LifesFinale(this);
    }
}

class LifesFinaleEffect extends OneShotEffect {

    public LifesFinaleEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy all creatures, then search target opponent's library for up to three creature cards and put them into their graveyard. Then that player shuffles";
    }

    private LifesFinaleEffect(final LifesFinaleEffect effect) {
        super(effect);
    }

    @Override
    public LifesFinaleEffect copy() {
        return new LifesFinaleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURES, source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            permanent.destroy(source, game, false);
        }

        Player opponent = game.getPlayer(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && opponent != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(0, 3, new FilterCreatureCard("creature cards from their library to put in their graveyard"));
            if (player.searchLibrary(target, source, game, opponent.getId())) {
                player.moveCards(new CardsImpl(target.getTargets()), Zone.GRAVEYARD, source, game);
            }
            opponent.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
