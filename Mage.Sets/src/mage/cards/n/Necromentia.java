package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

import java.util.HashSet;
import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class Necromentia extends CardImpl {

    public Necromentia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Choose a card name other than a basic land card name. Search target opponent's graveyard, hand, and library for any number of cards with that name and exile them. That player shuffles their library, then creates a 2/2 black Zombie creature token for each card exiled from their hand this way.
        this.getSpellAbility().addEffect(
                new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.NOT_BASIC_LAND_NAME)
        );
        this.getSpellAbility().addEffect(new NecromentiaEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private Necromentia(final Necromentia card) {
        super(card);
    }

    @Override
    public Necromentia copy() {
        return new Necromentia(this);
    }
}

class NecromentiaEffect extends OneShotEffect {

    NecromentiaEffect() {
        super(Outcome.Benefit);
        staticText = "Search target opponent's graveyard, hand, and library for any number of cards with that name and exile them. That player shuffles, then creates a 2/2 black Zombie creature token for each card exiled from their hand this way";
    }

    private NecromentiaEffect(NecromentiaEffect effect) {
        super(effect);
    }

    @Override
    public NecromentiaEffect copy() {
        return new NecromentiaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        Player controller = game.getPlayer(source.getControllerId());
        if (cardName != null && controller != null) {
            FilterCard filter = new FilterCard("card named " + cardName);
            filter.add(new NamePredicate(cardName));
            Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));

            // cards in Graveyard
            int cardsCount = (cardName.isEmpty() ? 0 : targetPlayer.getGraveyard().count(filter, game));
            if (cardsCount > 0) {
                filter.setMessage("card named " + cardName + " in the graveyard of " + targetPlayer.getName());
                TargetCard target = new TargetCard(0, cardsCount, Zone.GRAVEYARD, filter);
                if (controller.choose(Outcome.Exile, targetPlayer.getGraveyard(), target, source, game)) {
                    controller.moveCards(new CardsImpl(target.getTargets()), Zone.EXILED, source, game);
                }
            }

            // cards in Hand
            int numberOfCardsExiledFromHand = 0;
            cardsCount = (cardName.isEmpty() ? 0 : targetPlayer.getHand().count(filter, game));
            if (cardsCount > 0) {
                filter.setMessage("card named " + cardName + " in the hand of " + targetPlayer.getName());
                TargetCard target = new TargetCard(0, cardsCount, Zone.HAND, filter);
                if (controller.choose(Outcome.Exile, targetPlayer.getHand(), target, source, game)) {
                    numberOfCardsExiledFromHand = target.getTargets().size();
                    controller.moveCards(new CardsImpl(target.getTargets()), Zone.EXILED, source, game);
                }
            } else {
                targetPlayer.revealCards(targetPlayer.getName() + "'s Hand", targetPlayer.getHand(), game);
            }

            // cards in Library
            Cards cardsInLibrary = new CardsImpl();
            cardsInLibrary.addAllCards(targetPlayer.getLibrary().getCards(game));
            cardsCount = (cardName.isEmpty() ? 0 : cardsInLibrary.count(filter, game));
            if (cardsCount > 0) {
                filter.setMessage("card named " + cardName + " in the library of " + targetPlayer.getLogName());
                TargetCardInLibrary targetLib = new TargetCardInLibrary(0, cardsCount, filter);
                if (controller.choose(Outcome.Exile, cardsInLibrary, targetLib, source, game)) {
                    controller.moveCards(new CardsImpl(targetLib.getTargets()), Zone.EXILED, source, game);
                }
            } else {
                targetPlayer.revealCards(targetPlayer.getName() + "'s Library", new CardsImpl(new HashSet<>(targetPlayer.getLibrary().getCards(game))), game);
            }

            targetPlayer.shuffleLibrary(source, game);

            if (numberOfCardsExiledFromHand > 0) {
                game.getState().applyEffects(game);
                Token zombieToken = new ZombieToken();
                zombieToken.putOntoBattlefield(numberOfCardsExiledFromHand, game, source, targetPlayer.getId());
            }
            return true;
        }
        return false;
    }
}