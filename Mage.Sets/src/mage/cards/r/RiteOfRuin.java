
package mage.cards.r;

import java.util.*;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public final class RiteOfRuin extends CardImpl {

    public RiteOfRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}{R}");

        // Choose an order for artifacts, creatures, and lands. Each player sacrifices one permanent of the first type, sacrifices two of the second type, then sacrifices three of the third type.
        this.getSpellAbility().addEffect(new RiteOfRuinEffect());
    }

    private RiteOfRuin(final RiteOfRuin card) {
        super(card);
    }

    @Override
    public RiteOfRuin copy() {
        return new RiteOfRuin(this);
    }
}

class RiteOfRuinEffect extends OneShotEffect {

    public RiteOfRuinEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Choose an order for artifacts, creatures, and lands. Each player sacrifices one permanent of the first type, sacrifices two of the second type, then sacrifices three of the third type";
    }

    public RiteOfRuinEffect(final RiteOfRuinEffect effect) {
        super(effect);
    }

    @Override
    public RiteOfRuinEffect copy() {
        return new RiteOfRuinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Set<String> choices = new LinkedHashSet<>();
        choices.add("Artifacts");
        choices.add("Creatures");
        choices.add("Lands");

        LinkedList<CardType> order = new LinkedList<>();
        ChoiceImpl choice = new ChoiceImpl(true);
        choice.setMessage("Choose a card type");
        choice.setChoices(choices);

        while (choices.size() > 1) {
            if (!controller.choose(Outcome.Sacrifice, choice, game)) {
                return false;
            }
            order.add(getCardType(choice.getChoice()));
            choices.remove(choice.getChoice());
            choice.clearChoice();
        }
        order.add(getCardType(choices.iterator().next()));

        int count = 1;
        for (CardType cardType : order) {
            FilterControlledPermanent filter = new FilterControlledPermanent(cardType + " you control");
            filter.add(cardType.getPredicate());
            new SacrificeAllEffect(count, filter).apply(game, source);
            count++;
        }

        return true;
    }

    private CardType getCardType(String type) {
        if ("Artifacts".equals(type)) {
            return CardType.ARTIFACT;
        }
        if ("Creatures".equals(type)) {
            return CardType.CREATURE;
        }
        if ("Lands".equals(type)) {
            return CardType.LAND;
        }
        return null;
    }
}
