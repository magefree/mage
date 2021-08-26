package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SphereOfAnnihilation extends CardImpl {

    public SphereOfAnnihilation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{X}{B}");

        // Sphere of Annihilation enters the battlefield with X void counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new EntersBattlefieldWithXCountersEffect(CounterType.VOID.createInstance())
        ));

        // At the beginning of your upkeep, exile Sphere of Annihilation, all creatures and planeswalkers with mana value less than or equal to the number of void counters on it, and all creature and planeswalker cards in all graveyards with mana value less than or equal to the number of void counters on it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new SphereOfAnnihilationEffect(), TargetController.YOU, false
        ));
    }

    private SphereOfAnnihilation(final SphereOfAnnihilation card) {
        super(card);
    }

    @Override
    public SphereOfAnnihilation copy() {
        return new SphereOfAnnihilation(this);
    }
}

class SphereOfAnnihilationEffect extends OneShotEffect {

    SphereOfAnnihilationEffect() {
        super(Outcome.Benefit);
        staticText = "exile {this}, all creatures and planeswalkers with mana value less than or equal to " +
                "the number of void counters on it, and all creature and planeswalker cards in graveyards " +
                "with mana value less than or equal to the number of void counters on it";
    }

    private SphereOfAnnihilationEffect(final SphereOfAnnihilationEffect effect) {
        super(effect);
    }

    @Override
    public SphereOfAnnihilationEffect copy() {
        return new SphereOfAnnihilationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (player == null || permanent == null) {
            return false;
        }
        Cards cards = new CardsImpl(permanent);
        int counters = permanent.getCounters(game).getCount(CounterType.VOID);
        FilterPermanent filter = StaticFilters.FILTER_PERMANENT_CREATURE_OR_PLANESWALKER.copy();
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, counters + 1));
        cards.addAll(game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game));
        game.getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .map(g -> g.getCards(game))
                .flatMap(Collection::stream)
                .filter(card -> card.isCreature(game) || card.isPlaneswalker(game))
                .filter(card -> card.getManaValue() <= counters)
                .forEach(cards::add);
        return player.moveCards(cards, Zone.EXILED, source, game);
    }
}
