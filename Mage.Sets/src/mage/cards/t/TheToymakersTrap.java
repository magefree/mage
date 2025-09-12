package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;
import mage.util.RandomUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class TheToymakersTrap extends CardImpl {

    public TheToymakersTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // At the beginning of your upkeep, secretly choose a number between 1 and 5 that hasn't been chosen. If you do, an opponent guesses which number you chose, then you reveal the number you chose. If they guessed wrong, they lose life equal to the number they guessed and you draw a card. If they guessed right, sacrifice The Toymaker's Trap.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new TheToymakersTrapEffect()));
    }

    private TheToymakersTrap(final TheToymakersTrap card) {
        super(card);
    }

    @Override
    public TheToymakersTrap copy() {
        return new TheToymakersTrap(this);
    }
}

class TheToymakersTrapEffect extends OneShotEffect {

    private static final Set<String> options = new HashSet<>(Arrays.asList("1", "2", "3", "4", "5"));

    TheToymakersTrapEffect() {
        super(Outcome.Benefit);
        staticText = "secretly choose a number between 1 and 5 that hasn't been chosen. If you do, " +
                "an opponent guesses which number you chose, then you reveal the number you chose. " +
                "If they guessed wrong, they lose life equal to the number they guessed and you draw a card. " +
                "If they guessed right, sacrifice {this}";
    }

    private TheToymakersTrapEffect(final TheToymakersTrapEffect effect) {
        super(effect);
    }

    @Override
    public TheToymakersTrapEffect copy() {
        return new TheToymakersTrapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        List<String> alreadyChosen = getOrSetValue(game, source);
        Set<String> choices = new HashSet<>(options);
        choices.removeIf(alreadyChosen::contains);
        if (choices.isEmpty()) {
            return false;
        }
        String number;
        switch (choices.size()) {
            case 0:
                return false;
            case 1:
                number = RandomUtil.randomFromCollection(choices);
                break;
            default:
                Choice choice = new ChoiceImpl(true);
                choice.setMessage("Secretly choose a number which hasn't been chosen");
                choice.setChoices(choices);
                controller.choose(outcome, choice, game);
                number = choice.getChoice();
        }
        game.informPlayers(controller.getLogName() + " has secretly chosen a number");
        TargetPlayer target = new TargetOpponent(true);
        controller.choose(outcome, target, source, game);
        Player opponent = game.getPlayer(target.getFirstTarget());
        if (opponent == null) {
            return false;
        }
        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Guess which number was chosen");
        choice.setChoices(options);
        opponent.choose(outcome, choice, game);
        String guess = choice.getChoice();
        game.informPlayers(opponent.getLogName() + " has guessed " + guess);
        game.informPlayers("The chosen number was " + number);
        alreadyChosen.add(number);
        alreadyChosen.sort(String::compareTo);
        Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                .ifPresent(permanent -> permanent.addInfo(
                        "CHOSEN_NUMBERS", CardUtil.addToolTipMarkTags(
                                "Chosen numbers: " + alreadyChosen.stream().collect(Collectors.joining(", "))
                        ), game
                ));
        if (!Objects.equals(number, guess)) {
            opponent.loseLife(Integer.parseInt(guess), game, source, false);
            controller.drawCards(1, source, game);
        } else {
            Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                    .ifPresent(permanent -> permanent.sacrifice(source, game));
        }
        return true;
    }

    private static List<String> getOrSetValue(Game game, Ability source) {
        String key = "chosenNumbers_" + source.getControllerId() + '_' + source.getStackMomentSourceZCC();
        List<String> list = (List<String>) game.getState().getValue(key);
        if (list != null) {
            return list;
        }
        return game.getState().setValue(key, new ArrayList<>());
    }
}
