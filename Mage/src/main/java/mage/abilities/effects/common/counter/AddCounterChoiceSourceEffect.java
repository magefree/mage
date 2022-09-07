package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public class AddCounterChoiceSourceEffect extends OneShotEffect {

    private final List<CounterType> counterTypes;

    public AddCounterChoiceSourceEffect(CounterType ... counterTypes) {
        super(Outcome.Benefit);
        this.counterTypes = Arrays.stream(counterTypes).collect(Collectors.toList());
        this.createStaticText();
    }

    private AddCounterChoiceSourceEffect(final AddCounterChoiceSourceEffect effect) {
        super(effect);
        this.counterTypes = new ArrayList<>(effect.counterTypes);
    }

    private void createStaticText() {
        switch (this.counterTypes.size()) {
            case 0:
            case 1:
                throw new IllegalArgumentException("AddCounterChoiceSourceEffect should be called with at least 2 " +
                        "counter types, it was called with: " + this.counterTypes);
            case 2:
                this.staticText = "with your choice of a " + this.counterTypes.get(0) +
                        " counter or a " + this.counterTypes.get(1) + " counter on it";
                break;
            default:
                List<String> strings = this.counterTypes.stream().map(CounterType::toString).collect(Collectors.toList());
                this.staticText = CardUtil.concatWithOr(strings);
                break;
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (player == null || permanent == null) {
            return false;
        }

        Choice counterChoice = new ChoiceImpl();
        counterChoice.setMessage("Choose counter type");
        counterChoice.setChoices(
                this.counterTypes
                        .stream()
                        .map(counterType -> AddCounterChoiceSourceEffect.capitalize(counterType.getName()))
                        .collect(Collectors.toSet())
        );

        if (!player.choose(Outcome.Neutral, counterChoice, game)) {
            return false;
        }

        CounterType counterChosen = CounterType.findByName(counterChoice.getChoice().toLowerCase(Locale.ENGLISH));
        if (counterChosen == null || !this.counterTypes.contains(counterChosen)) {
            return false;
        }
        Counter counter = counterChosen.createInstance();

        return permanent.addCounters(counter, source.getControllerId(), source, game);
    }

    private static String capitalize(String string) {
        return string != null ? string.substring(0, 1).toUpperCase(Locale.ENGLISH) + string.substring(1) : null;
    }

    @Override
    public AddCounterChoiceSourceEffect copy() {
        return new AddCounterChoiceSourceEffect(this);
    }
}
