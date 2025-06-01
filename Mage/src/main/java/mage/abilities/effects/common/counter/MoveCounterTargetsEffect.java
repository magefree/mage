package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.EachTargetPointer;
import mage.util.RandomUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Styxo
 */
public class MoveCounterTargetsEffect extends OneShotEffect {

    private final CounterType counterType;

    public MoveCounterTargetsEffect() {
        this((CounterType) null);
    }

    public MoveCounterTargetsEffect(CounterType counterType) {
        super(Outcome.Detriment);
        this.counterType = counterType;
        this.setTargetPointer(new EachTargetPointer());
    }

    protected MoveCounterTargetsEffect(final MoveCounterTargetsEffect effect) {
        super(effect);
        this.counterType = effect.counterType;
    }

    @Override
    public MoveCounterTargetsEffect copy() {
        return new MoveCounterTargetsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = this
                .getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (permanents.size() < 2) {
            return false;
        }
        Permanent fromPermanent = permanents.get(0);
        if (counterType != null && !fromPermanent.getCounters(game).containsKey(counterType)) {
            return false;
        }
        CounterType typeToRemove;
        if (counterType == null) {
            Set<String> types = new HashSet<>(fromPermanent.getCounters(game).keySet());
            switch (types.size()) {
                case 0:
                    return false;
                case 1:
                    typeToRemove = CounterType.findByName(RandomUtil.randomFromCollection(types));
                    break;
                default:
                    Player player = game.getPlayer(source.getControllerId());
                    if (player == null) {
                        return false;
                    }
                    Choice choice = new ChoiceImpl(true);
                    choice.setChoices(types);
                    choice.setMessage("Choose a type of counter to move");
                    player.choose(Outcome.BoostCreature, choice, game);
                    typeToRemove = CounterType.findByName(choice.getChoice());
            }
        } else {
            typeToRemove = counterType;
        }
        if (typeToRemove == null) {
            return false;
        }
        Permanent toPermanent = permanents.get(1);
        if (!toPermanent.addCounters(typeToRemove.createInstance(), source, game)) {
            return false;
        }
        fromPermanent.removeCounters(typeToRemove.createInstance(), source, game);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("move ");
        sb.append(Optional
                .ofNullable(counterType)
                .map(c -> counterType.getArticle() + ' ' + counterType.getName())
                .orElse("a"));
        sb.append(" counter from ");
        sb.append(mode.getTargets().get(0).getDescription());
        sb.append(" onto ");
        sb.append(mode.getTargets().get(1).getDescription());
        return sb.toString();
    }
}
