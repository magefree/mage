package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LegionsEnd extends CardImpl {

    private static final FilterPermanent filter
            = new FilterOpponentsCreaturePermanent("creature an opponent controls with mana value 2 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public LegionsEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Exile target creature an opponent controls with converted mana cost 2 or less and all other creatures that player controls with the same name as that creature. Then that player reveals their hand and exiles all cards with that name from their hand and graveyard.
        this.getSpellAbility().addEffect(new LegionsEndEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private LegionsEnd(final LegionsEnd card) {
        super(card);
    }

    @Override
    public LegionsEnd copy() {
        return new LegionsEnd(this);
    }
}

class LegionsEndEffect extends OneShotEffect {

    LegionsEndEffect() {
        super(Outcome.Benefit);
        staticText = "Exile target creature an opponent controls with mana value 2 or less " +
                "and all other creatures that player controls with the same name as that creature. " +
                "Then that player reveals their hand and exiles all cards with that name from their hand and graveyard.";
    }

    private LegionsEndEffect(final LegionsEndEffect effect) {
        super(effect);
    }

    @Override
    public LegionsEndEffect copy() {
        return new LegionsEndEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        if (player == null) {
            return false;
        }
        String name = permanent.getName();
        if (name == null || name.equals("")) {
            player.revealCards(source, player.getHand(), game);
            return player.moveCards(permanent, Zone.EXILED, source, game);
        }
        Cards cards = new CardsImpl();
        game.getBattlefield()
                .getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, player.getId(), game)
                .stream()
                .filter(perm -> name.equals(perm.getName()))
                .forEach(cards::add);

        player.revealCards(source, player.getHand(), game);

        player.getHand()
                .getCards(game)
                .stream()
                .filter(card -> name.equals(card.getName()))
                .forEach(cards::add);

        player.getGraveyard()
                .getCards(game)
                .stream()
                .filter(card -> name.equals(card.getName()))
                .forEach(cards::add);

        return player.moveCards(cards, Zone.EXILED, source, game);
    }
}