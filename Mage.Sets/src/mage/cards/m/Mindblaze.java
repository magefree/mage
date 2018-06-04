
package mage.cards.m;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class Mindblaze extends CardImpl {

    public Mindblaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}");

        // Name a nonland card and choose a number greater than 0. Target player reveals their library.
        // If that library contains exactly the chosen number of the named card,
        // Mindblaze deals 8 damage to that player.
        // Then that player shuffles their library.
        this.getSpellAbility().addEffect(new MindblazeEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public Mindblaze(final Mindblaze card) {
        super(card);
    }

    @Override
    public Mindblaze copy() {
        return new Mindblaze(this);
    }

}

class MindblazeEffect extends OneShotEffect {

    MindblazeEffect() {
        super(Outcome.Damage);
        staticText = "Name a nonland card and choose a number greater than 0. Target player reveals their library. If that library contains exactly the chosen number of the named card, {this} deals 8 damage to that player. Then that player shuffles their library";
    }

    MindblazeEffect(final MindblazeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        Player playerControls = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (player != null && playerControls != null && sourceObject != null) {
            Choice cardChoice = new ChoiceImpl();
            cardChoice.setChoices(CardRepository.instance.getNonLandNames());
            cardChoice.clearChoice();
            Choice numberChoice = new ChoiceImpl();
            numberChoice.setMessage("Choose a number greater than 0");
            Set<String> numbers = new HashSet<>();
            for (int i = 1; i <= 4; i++) {
                numbers.add(Integer.toString(i));
            }
            numberChoice.setChoices(numbers);

            if (!playerControls.choose(Outcome.Neutral, cardChoice, game)) {
                return false;
            }
            if (!playerControls.choose(Outcome.Neutral, numberChoice, game)) {
                return false;
            }

            game.informPlayers(sourceObject.getIdName() + " - Named card: [" + cardChoice.getChoice() + "] - Chosen number: [" + numberChoice.getChoice() + ']');

            Cards cards = new CardsImpl();
            cards.addAll(player.getLibrary().getCards(game));
            playerControls.revealCards("Library", cards, game);
            FilterCard filter = new FilterCard();
            filter.add(new NamePredicate(cardChoice.getChoice()));
            int count = Integer.parseInt(numberChoice.getChoice());
            if (player.getLibrary().count(filter, game) == count) {
                player.damage(8, source.getSourceId(), game.copy(), false, true);
            }
            player.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

    @Override
    public MindblazeEffect copy() {
        return new MindblazeEffect(this);
    }

}
