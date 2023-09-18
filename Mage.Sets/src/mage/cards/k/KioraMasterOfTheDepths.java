package mage.cards.k;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.command.emblems.KioraMasterOfTheDepthsEmblem;
import mage.game.permanent.token.OctopusToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

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

    private static final FilterCard creatureFilter
            = new FilterCreatureCard("creature card to put into your hand");
    private static final FilterCard landFilter
            = new FilterLandCard("land card to put into your hand");

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
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null || sourceObject == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 4));
        if (cards.isEmpty()) {
            return false;
        }

        controller.revealCards(sourceObject.getName(), cards, game);

        boolean creatureCardFound = cards.getCards(game).stream().anyMatch(card -> card.isCreature(game));
        boolean landCardFound = cards.getCards(game).stream().anyMatch(card -> card.isLand(game));

        if (!creatureCardFound && !landCardFound) {
            controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            return true;
        }

        Cards cardsToHand = new CardsImpl();

        if (creatureCardFound) {
            TargetCard target = new TargetCardInLibrary(0, 1, creatureFilter);
            controller.chooseTarget(Outcome.DrawCard, cards, target, source, game);
            if (target.getFirstTarget() != null) {
                cards.remove(target.getFirstTarget());
                cardsToHand.add(target.getFirstTarget());
            }
        }
        if (landCardFound) {
            TargetCard target = new TargetCardInLibrary(0, 1, landFilter);
            controller.chooseTarget(Outcome.DrawCard, cards, target, source, game);
            if (target.getFirstTarget() != null) {
                cards.remove(target.getFirstTarget());
                cardsToHand.add(target.getFirstTarget());
            }
        }
        controller.moveCards(cardsToHand, Zone.HAND, source, game);
        controller.moveCards(cards, Zone.GRAVEYARD, source, game);
        return true;
    }
}
