package mage.cards.b;

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
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodOath extends CardImpl {

    public BloodOath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Choose a card type. Target opponent reveals their hand. Blood Oath deals 3 damage to that player for each card of the chosen type revealed this way.
        this.getSpellAbility().addEffect(new BloodOathEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private BloodOath(final BloodOath card) {
        super(card);
    }

    @Override
    public BloodOath copy() {
        return new BloodOath(this);
    }
}

class BloodOathEffect extends OneShotEffect {

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

    public BloodOathEffect() {
        super(Outcome.Benefit);
        staticText = "Choose a card type. Target opponent reveals their hand. {this} deals 3 damage to that player for each card of the chosen type revealed this way";
    }

    public BloodOathEffect(final BloodOathEffect effect) {
        super(effect);
    }

    @Override
    public BloodOathEffect copy() {
        return new BloodOathEffect(this);
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
                String chosenType = choiceImpl.getChoice();

                if (chosenType.equals(CardType.ARTIFACT.toString())) {
                    type = CardType.ARTIFACT;
                } else if (chosenType.equals(CardType.LAND.toString())) {
                    type = CardType.LAND;
                } else if (chosenType.equals(CardType.CREATURE.toString())) {
                    type = CardType.CREATURE;
                } else if (chosenType.equals(CardType.ENCHANTMENT.toString())) {
                    type = CardType.ENCHANTMENT;
                } else if (chosenType.equals(CardType.INSTANT.toString())) {
                    type = CardType.INSTANT;
                } else if (chosenType.equals(CardType.SORCERY.toString())) {
                    type = CardType.SORCERY;
                } else if (chosenType.equals(CardType.PLANESWALKER.toString())) {
                    type = CardType.PLANESWALKER;
                } else if (chosenType.equals(CardType.TRIBAL.toString())) {
                    type = CardType.TRIBAL;
                }
                if (type != null) {
                    Cards hand = opponent.getHand();
                    opponent.revealCards(sourceObject.getIdName(), hand, game);
                    Set<Card> cards = hand.getCards(game);
                    int damageToDeal = 0;
                    for (Card card : cards) {
                        if (card != null && card.getCardType(game).contains(type)) {
                            damageToDeal += 3;
                        }
                    }
                    game.informPlayers(sourceObject.getLogName() + " deals " + (damageToDeal == 0 ? "no" : "" + damageToDeal) + " damage to " + opponent.getLogName());
                    opponent.damage(damageToDeal, source.getSourceId(), source, game);
                    return true;
                }
            }
        }
        return false;
    }
}
