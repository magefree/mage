package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.RhinoWarriorToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VivienOnTheHunt extends CardImpl {

    public VivienOnTheHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VIVIEN);
        this.setStartingLoyalty(4);

        // +2: You may sacrifice a creature. If you do, search your library for a creature card with mana value equal to 1 plus the sacrificed creature's mana value, put it onto the battlefield, then shuffle.
        this.addAbility(new LoyaltyAbility(new VivienOnTheHuntSacrificeEffect(), 2));

        // +1: Mill five cards, then put any number of creature cards milled this way into your hand.
        this.addAbility(new LoyaltyAbility(new VivienOnTheHuntMillEffect(), 1));

        // −1: Create a 4/4 green Rhino Warrior creature token.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new RhinoWarriorToken()), -1));
    }

    private VivienOnTheHunt(final VivienOnTheHunt card) {
        super(card);
    }

    @Override
    public VivienOnTheHunt copy() {
        return new VivienOnTheHunt(this);
    }
}

class VivienOnTheHuntSacrificeEffect extends OneShotEffect {

    VivienOnTheHuntSacrificeEffect() {
        super(Outcome.Benefit);
        staticText = "you may sacrifice a creature. If you do, search your library for a creature card with mana " +
                "value equal to 1 plus the sacrificed creature's mana value, put it onto the battlefield, then shuffle";
    }

    private VivienOnTheHuntSacrificeEffect(final VivienOnTheHuntSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public VivienOnTheHuntSacrificeEffect copy() {
        return new VivienOnTheHuntSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(
                0, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, true
        );
        player.choose(Outcome.Sacrifice, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null || !permanent.sacrifice(source, game)) {
            return false;
        }
        int mv = permanent.getManaValue() + 1;
        FilterCard filter = new FilterCreatureCard("creature card with mana value " + mv);
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, mv));
        TargetCardInLibrary targetCardInLibrary = new TargetCardInLibrary(filter);
        player.searchLibrary(targetCardInLibrary, source, game);
        Card card = player.getLibrary().getCard(targetCardInLibrary.getFirstTarget(), game);
        if (card != null) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        player.shuffleLibrary(source, game);
        return true;
    }
}

class VivienOnTheHuntMillEffect extends OneShotEffect {

    VivienOnTheHuntMillEffect() {
        super(Outcome.Benefit);
        staticText = "mill five cards, then put any number of creature cards milled this way into your hand";
    }

    private VivienOnTheHuntMillEffect(final VivienOnTheHuntMillEffect effect) {
        super(effect);
    }

    @Override
    public VivienOnTheHuntMillEffect copy() {
        return new VivienOnTheHuntMillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = player.millCards(5, source, game);
        if (cards.count(StaticFilters.FILTER_CARD_CREATURE, game) < 1) {
            return true;
        }
        TargetCard target = new TargetCard(
                0, Integer.MAX_VALUE, Zone.ALL, StaticFilters.FILTER_CARD_CREATURE
        );
        player.choose(outcome, cards, target, source, game);
        player.moveCards(new CardsImpl(target.getTargets()), Zone.HAND, source, game);
        return true;
    }
}
