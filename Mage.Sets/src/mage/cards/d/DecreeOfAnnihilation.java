package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class DecreeOfAnnihilation extends CardImpl {

    public DecreeOfAnnihilation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{8}{R}{R}");

        // Exile all artifacts, creatures, and lands from the battlefield, all cards from all graveyards, and all cards from all hands.
        this.getSpellAbility().addEffect(new DecreeOfAnnihilationEffect());

        // Cycling {5}{R}{R}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{5}{R}{R}")));

        // When you cycle Decree of Annihilation, destroy all lands.
        this.addAbility(new CycleTriggeredAbility(new DestroyAllEffect(StaticFilters.FILTER_LANDS), false));
    }

    private DecreeOfAnnihilation(final DecreeOfAnnihilation card) {
        super(card);
    }

    @Override
    public DecreeOfAnnihilation copy() {
        return new DecreeOfAnnihilation(this);
    }
}

class DecreeOfAnnihilationEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    DecreeOfAnnihilationEffect() {
        super(Outcome.Detriment);
        staticText = "Exile all artifacts, creatures, and lands from the battlefield, " +
                "all cards from all graveyards, and all cards from all hands";
    }

    private DecreeOfAnnihilationEffect(final DecreeOfAnnihilationEffect effect) {
        super(effect);
    }

    @Override
    public DecreeOfAnnihilationEffect copy() {
        return new DecreeOfAnnihilationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        )) {
            cards.add(permanent);
        }
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            cards.addAll(player.getHand());
            cards.addAll(player.getGraveyard());
        }
        return controller.moveCards(cards, Zone.EXILED, source, game);
    }
}
