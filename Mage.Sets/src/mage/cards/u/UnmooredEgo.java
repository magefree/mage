package mage.cards.u;

import java.util.UUID;
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
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class UnmooredEgo extends CardImpl {

    public UnmooredEgo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{B}");

        // Choose a card name. Search target opponent's graveyard, hand, and library for up to four cards with that name and exile them. That player shuffles their library, then draws a card for each card exiled from their hand this way.
        this.getSpellAbility().addEffect((new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL)));
        this.getSpellAbility().addEffect(new UnmooredEgoEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private UnmooredEgo(final UnmooredEgo card) {
        super(card);
    }

    @Override
    public UnmooredEgo copy() {
        return new UnmooredEgo(this);
    }
}

class UnmooredEgoEffect extends OneShotEffect {

    public UnmooredEgoEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search target opponent's graveyard, hand, and library for up to four cards with that name and exile them. That player shuffles, then draws a card for each card exiled from their hand this way";
    }

    public UnmooredEgoEffect(final UnmooredEgoEffect effect) {
        super(effect);
    }

    @Override
    public UnmooredEgoEffect copy() {
        return new UnmooredEgoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        Player controller = game.getPlayer(source.getControllerId());
        if (cardName != null && controller != null) {
            int numberOfCardsStillToRemove = 4;
            int numberOfCardsExiledFromHand = 0;
            Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (targetPlayer != null) {
                FilterCard filter = new FilterCard("card named " + cardName);
                filter.add(new NamePredicate(cardName));

                // cards in Graveyard
                int cardsCount = (cardName.isEmpty() ? 0 : targetPlayer.getGraveyard().count(filter, game));
                if (cardsCount > 0) {
                    filter.setMessage("card named " + cardName + " in the graveyard of " + targetPlayer.getName());
                    TargetCard target = new TargetCard(Math.min(cardsCount, numberOfCardsStillToRemove),
                            Math.min(cardsCount, numberOfCardsStillToRemove), Zone.GRAVEYARD, filter);
                    if (controller.choose(Outcome.Exile, targetPlayer.getGraveyard(), target, source, game)) {
                        numberOfCardsStillToRemove -= target.getTargets().size();
                        controller.moveCards(new CardsImpl(target.getTargets()), Zone.EXILED, source, game);
                    }
                }

                // cards in Hand
                if (numberOfCardsStillToRemove > 0) {
                    cardsCount = (cardName.isEmpty() ? 0 : targetPlayer.getHand().count(filter, game));
                    filter.setMessage("card named " + cardName + " in the hand of " + targetPlayer.getName());
                    TargetCard target = new TargetCard(0, Math.min(cardsCount, numberOfCardsStillToRemove), Zone.HAND, filter);
                    if (controller.choose(Outcome.Exile, targetPlayer.getHand(), target, source, game)) {
                        numberOfCardsExiledFromHand = target.getTargets().size();
                        numberOfCardsStillToRemove -= target.getTargets().size();
                        controller.moveCards(new CardsImpl(target.getTargets()), Zone.EXILED, source, game);
                    }
                }

                // cards in Library
                if (numberOfCardsStillToRemove > 0) {
                    Cards cardsInLibrary = new CardsImpl();
                    cardsInLibrary.addAllCards(targetPlayer.getLibrary().getCards(game));
                    cardsCount = (cardName.isEmpty() ? 0 : cardsInLibrary.count(filter, game));
                    filter.setMessage("card named " + cardName + " in the library of " + targetPlayer.getLogName());
                    TargetCardInLibrary targetLib = new TargetCardInLibrary(0, Math.min(cardsCount, numberOfCardsStillToRemove), filter);
                    if (controller.choose(Outcome.Exile, cardsInLibrary, targetLib, source, game)) {
                        controller.moveCards(new CardsImpl(targetLib.getTargets()), Zone.EXILED, source, game);
                    }
                }
                targetPlayer.shuffleLibrary(source, game);

                if (numberOfCardsExiledFromHand > 0) {
                    game.getState().applyEffects(game);
                    targetPlayer.drawCards(numberOfCardsExiledFromHand, source, game);
                }
            }

            return true;
        }

        return false;
    }

}
