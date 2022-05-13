package mage.cards.v;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author noahg
 */
public final class VigeanIntuition extends CardImpl {

    public VigeanIntuition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}{U}");


        // Choose a card type, then reveal the top four cards of your library. Put all cards of the chosen type revealed this way into your hand and the rest into your graveyard.
        this.getSpellAbility().addEffect(new VigeanIntuitionEffect());
    }

    private VigeanIntuition(final VigeanIntuition card) {
        super(card);
    }

    @Override
    public VigeanIntuition copy() {
        return new VigeanIntuition(this);
    }
}

class VigeanIntuitionEffect extends OneShotEffect {

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

    public VigeanIntuitionEffect() {
        super(Outcome.Benefit);
        staticText = "Choose a card type, then reveal the top four cards of your library. Put all cards of the chosen type revealed this way into your hand and the rest into your graveyard";
    }

    public VigeanIntuitionEffect(final VigeanIntuitionEffect effect) {
        super(effect);
    }

    @Override
    public VigeanIntuitionEffect copy() {
        return new VigeanIntuitionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source);
        Player player = game.getPlayer(source.getControllerId());
        if (sourceObject == null || player == null) {
            return false;
        }
        Library library = player.getLibrary();
        if (library == null) {
            return false;
        }

        Choice choiceImpl = new ChoiceImpl();
        choiceImpl.setChoices(choice);
        if (player.choose(Outcome.Neutral, choiceImpl, game)) {
            String choosenType = choiceImpl.getChoice();
            if (choosenType == null || choosenType.isEmpty()) {
                return false;
            }
            CardType type = null;
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
                Set<Card> top = library.getTopCards(game, 4);
                player.revealCards(source, new CardsImpl(top), game);
                Cards putInHand = new CardsImpl();
                Cards putInGraveyard = new CardsImpl();
                for (Card card : top) {
                    if (card != null && card.getCardType(game).contains(type)) {
                        putInHand.add(card);
                    } else {
                        putInGraveyard.add(card);
                    }
                }
                player.moveCards(putInHand, Zone.HAND, source, game);
                player.moveCards(putInGraveyard, Zone.GRAVEYARD, source, game);
                return true;
            }
        }
        return false;
    }
}