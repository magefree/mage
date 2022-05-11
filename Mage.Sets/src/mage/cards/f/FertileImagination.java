package mage.cards.f;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.SaprolingToken;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author noahg
 */
public final class FertileImagination extends CardImpl {

    public FertileImagination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{G}");
        

        // Choose a card type. Target opponent reveals their hand. Create two 1/1 green Saproling creature tokens for each card of the chosen type revealed this way.
        this.getSpellAbility().addEffect(new FertileImaginationEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private FertileImagination(final FertileImagination card) {
        super(card);
    }

    @Override
    public FertileImagination copy() {
        return new FertileImagination(this);
    }
}

class FertileImaginationEffect extends OneShotEffect {

    private static final Set<String> choice = new LinkedHashSet<>();

    static {
        choice.add(CardType.ARTIFACT.toString());
        choice.add(CardType.CREATURE.toString());
        choice.add(CardType.ENCHANTMENT.toString());
        choice.add(CardType.INSTANT.toString());
        choice.add(CardType.LAND.toString());
        choice.add(CardType.PLANESWALKER.toString());
        choice.add(CardType.SORCERY.toString());
        choice.add(CardType.TRIBAL.toString());
    }

    public FertileImaginationEffect() {
        super(Outcome.Benefit);
        staticText = "Choose a card type. Target opponent reveals their hand. Create two 1/1 green Saproling creature tokens for each card of the chosen type revealed this way";
    }

    public FertileImaginationEffect(final FertileImaginationEffect effect) {
        super(effect);
    }

    @Override
    public FertileImaginationEffect copy() {
        return new FertileImaginationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source);
        Player player = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (player != null && opponent != null && sourceObject != null) {
            Choice choiceImpl = new ChoiceImpl();
            choiceImpl.setChoices(choice);
            if (player.choose(Outcome.Neutral, choiceImpl, game)) {
                CardType type = null;
                String choosenType = choiceImpl.getChoice();

                if (choosenType.equals(CardType.ARTIFACT.toString())) {
                    type = CardType.ARTIFACT;
                } else if (choosenType.equals(CardType.LAND.toString())) {
                    type = CardType.LAND;
                } else if (choosenType.equals(CardType.CREATURE.toString())) {
                    type = CardType.CREATURE;
                } else if (choosenType.equals(CardType.ENCHANTMENT.toString())) {
                    type = CardType.ENCHANTMENT;
                } else if (choosenType.equals(CardType.INSTANT.toString())) {
                    type = CardType.INSTANT;
                } else if (choosenType.equals(CardType.SORCERY.toString())) {
                    type = CardType.SORCERY;
                } else if (choosenType.equals(CardType.PLANESWALKER.toString())) {
                    type = CardType.PLANESWALKER;
                } else if (choosenType.equals(CardType.TRIBAL.toString())) {
                    type = CardType.TRIBAL;
                }
                if (type != null) {
                    Cards hand = opponent.getHand();
                    SaprolingToken saprolingToken = new SaprolingToken();
                    opponent.revealCards(sourceObject.getIdName(), hand, game);
                    Set<Card> cards = hand.getCards(game);
                    int tokensToMake = 0;
                    for (Card card : cards) {
                        if (card != null && card.getCardType(game).contains(type)) {
                            tokensToMake += 2;
                        }
                    }
                    game.informPlayers(sourceObject.getLogName() + " creates " + (tokensToMake == 0 ? "no" : "" + tokensToMake) + " 1/1 green Saproling creature tokens.");
                    saprolingToken.putOntoBattlefield(tokensToMake, game, source, source.getControllerId());
                    return true;
                }
            }
        }
        return false;
    }
}
