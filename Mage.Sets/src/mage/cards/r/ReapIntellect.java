package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SharesNamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author jeffwadsworth
 */
public final class ReapIntellect extends CardImpl {

    public ReapIntellect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{2}{U}{B}");

        // Target opponent reveals their hand. You choose up to X nonland cards 
        // from it and exile them. For each card exiled this way, search that 
        // player's graveyard, hand, and library for any number of cards with the 
        // same name as that card and exile them. Then that player shuffles their library.
        this.getSpellAbility().addEffect(new ReapIntellectEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private ReapIntellect(final ReapIntellect card) {
        super(card);
    }

    @Override
    public ReapIntellect copy() {
        return new ReapIntellect(this);
    }
}

class ReapIntellectEffect extends OneShotEffect {

    public ReapIntellectEffect() {
        super(Outcome.Exile);
        staticText = "Target opponent reveals their hand. You choose up to X "
                + "nonland cards from it and exile them. For each card exiled "
                + "this way, search that player's graveyard, hand, and library "
                + "for any number of cards with the same name as that card and "
                + "exile them. Then that player shuffles";
    }

    private ReapIntellectEffect(final ReapIntellectEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || targetPlayer == null) {
            return false;
        }
        targetPlayer.revealCards(source, targetPlayer.getHand(), game);
        TargetCard target = new TargetCardInHand(
                0,
                GetXValue.instance.calculate(game, source, this),
                StaticFilters.FILTER_CARDS_NON_LAND
        );
        controller.chooseTarget(Outcome.Benefit, targetPlayer.getHand(), target, source, game);
        Cards exiledCards = new CardsImpl(target.getTargets());
        controller.moveCards(exiledCards, Zone.EXILED, source, game);
        exiledCards.retainZone(Zone.EXILED, game);
        if (exiledCards.isEmpty()) {
            targetPlayer.shuffleLibrary(source, game);
            return true;
        }
        FilterCard filterCard = new FilterCard("cards with the same name");
        filterCard.add(Predicates.or(
                exiledCards
                        .getCards(game)
                        .stream()
                        .map(SharesNamePredicate::new)
                        .collect(Collectors.toSet())
        ));
        exiledCards.clear();

        TargetCardInGraveyard targetCardInGraveyard = new TargetCardInGraveyard(0, Integer.MAX_VALUE, filterCard, true);
        controller.choose(Outcome.Exile, targetPlayer.getGraveyard(), targetCardInGraveyard, source, game);
        exiledCards.addAll(targetCardInGraveyard.getTargets());

        TargetCardInHand targetCardInHand = new TargetCardInHand(0, Integer.MAX_VALUE, filterCard);
        controller.choose(Outcome.Exile, targetPlayer.getHand(), targetCardInHand, source, game);
        exiledCards.addAll(targetCardInHand.getTargets());

        TargetCardInLibrary targetCardInLibrary = new TargetCardInLibrary(0, Integer.MAX_VALUE, filterCard);
        controller.searchLibrary(targetCardInLibrary, source, game, targetPlayer.getId());
        for (UUID cardId : targetCardInLibrary.getTargets()) {
            exiledCards.add(targetPlayer.getLibrary().getCard(cardId, game));
        }

        targetPlayer.shuffleLibrary(source, game);
        return true;
    }

    @Override
    public ReapIntellectEffect copy() {
        return new ReapIntellectEffect(this);
    }
}
