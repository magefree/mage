package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.emblems.KioraMasterOfTheDepthsEmblem;
import mage.game.permanent.token.OctopusToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardAndOrCardInLibrary;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author fireshoes
 */
public final class KioraMasterOfTheDepths extends CardImpl {

    public KioraMasterOfTheDepths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{G}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KIORA);

        this.setStartingLoyalty(4);

        // +1: Untap up to one target creature and up to one target land.
        LoyaltyAbility ability = new LoyaltyAbility(new KioraUntapEffect(), 1);
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_CREATURE, false));
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_LAND, false));
        this.addAbility(ability);

        // -2: Reveal the top four cards of your library. You may put a creature card and/or a land card from among them into your hand. Put the rest into your graveyard.
        this.addAbility(new LoyaltyAbility(new KioraRevealEffect(), -2));

        // -8: You get an emblem with "Whenever a creature enters the battlefield under your control, you may have it fight target creature." Then create three 8/8 blue Octopus creature tokens.
        ability = new LoyaltyAbility(new GetEmblemEffect(new KioraMasterOfTheDepthsEmblem()), -8);
        ability.addEffect(new CreateTokenEffect(new OctopusToken(), 3)
                .setText("Then create three 8/8 blue Octopus creature tokens"));
        this.addAbility(ability);
    }

    private KioraMasterOfTheDepths(final KioraMasterOfTheDepths card) {
        super(card);
    }

    @Override
    public KioraMasterOfTheDepths copy() {
        return new KioraMasterOfTheDepths(this);
    }
}

class KioraUntapEffect extends OneShotEffect {

    KioraUntapEffect() {
        super(Outcome.Untap);
        this.staticText = "Untap up to one target creature and up to one target land";
    }

    private KioraUntapEffect(final KioraUntapEffect effect) {
        super(effect);
    }

    @Override
    public KioraUntapEffect copy() {
        return new KioraUntapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        source.getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .forEachOrdered(permanent -> permanent.untap(game));
        return true;
    }
}

class KioraRevealEffect extends OneShotEffect {

    KioraRevealEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top four cards of your library. " +
                "You may put a creature card and/or a land card from among them into your hand." +
                " Put the rest into your graveyard";
    }

    private KioraRevealEffect(final KioraRevealEffect effect) {
        super(effect);
    }

    @Override
    public KioraRevealEffect copy() {
        return new KioraRevealEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 4));
        player.revealCards(source, cards, game);
        TargetCard target = new TargetCardAndOrCardInLibrary(CardType.CREATURE, CardType.LAND);
        player.choose(outcome, cards, target, source, game);
        Cards toHand = new CardsImpl();
        toHand.addAll(target.getTargets());
        player.moveCards(toHand, Zone.HAND, source, game);
        cards.removeAll(toHand);
        player.moveCards(cards, Zone.GRAVEYARD, source, game);
        return true;
    }
}
