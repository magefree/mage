
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class DecreeOfAnnihilation extends CardImpl {

    public DecreeOfAnnihilation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{8}{R}{R}");

        // Exile all artifacts, creatures, and lands from the battlefield, all cards from all graveyards, and all cards from all hands.
        this.getSpellAbility().addEffect(new DecreeOfAnnihilationEffect());

        // Cycling {5}{R}{R}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{5}{R}{R}")));

        // When you cycle Decree of Annihilation, destroy all lands.
        Ability ability = new CycleTriggeredAbility(new DestroyAllEffect(StaticFilters.FILTER_LANDS), false);
        this.addAbility(ability);
    }

    public DecreeOfAnnihilation(final DecreeOfAnnihilation card) {
        super(card);
    }

    @Override
    public DecreeOfAnnihilation copy() {
        return new DecreeOfAnnihilation(this);
    }
}

class DecreeOfAnnihilationEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)));
    }

    public DecreeOfAnnihilationEffect() {
        super(Outcome.Detriment);
        staticText = "Exile all artifacts, creatures, and lands from the battlefield, all cards from all graveyards, and all cards from all hands";
    }

    public DecreeOfAnnihilationEffect(final DecreeOfAnnihilationEffect effect) {
        super(effect);
    }

    @Override
    public DecreeOfAnnihilationEffect copy() {
        return new DecreeOfAnnihilationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            permanent.moveToExile(null, "", source.getSourceId(), game);
        }
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                for (UUID cid : player.getHand().copy()) {
                    Card c = game.getCard(cid);
                    if (c != null) {
                        c.moveToExile(null, null, source.getSourceId(), game);
                    }
                }
                for (UUID cid : player.getGraveyard().copy()) {
                    Card c = game.getCard(cid);
                    if (c != null) {
                        c.moveToExile(null, null, source.getSourceId(), game);
                    }
                }
            }
        }
        return true;
    }
}
