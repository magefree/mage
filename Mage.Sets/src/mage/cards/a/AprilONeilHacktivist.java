package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class AprilONeilHacktivist extends CardImpl {

    public AprilONeilHacktivist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // At the beginning of your end step, draw a card for each card type among spells you've cast this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new DrawCardSourceControllerEffect(AprilONeilHacktivistValue.instance)
        ).addHint(AprilONeilHacktivistHint.instance), new AprilONeilHacktivistWatcher());
    }

    private AprilONeilHacktivist(final AprilONeilHacktivist card) {
        super(card);
    }

    @Override
    public AprilONeilHacktivist copy() {
        return new AprilONeilHacktivist(this);
    }
}

enum AprilONeilHacktivistValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return AprilONeilHacktivistWatcher.getCardTypesCast(sourceAbility.getControllerId(), game).size();
    }

    @Override
    public AprilONeilHacktivistValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "card type among spells you've cast this turn";
    }

    @Override
    public String toString() {
        return "1";
    }
}

enum AprilONeilHacktivistHint implements Hint {
    instance;


    @Override
    public String getText(Game game, Ability ability) {
        List<String> types = AprilONeilHacktivistWatcher
                .getCardTypesCast(ability.getControllerId(), game)
                .stream()
                .map(CardType::toString)
                .sorted()
                .collect(Collectors.toList());
        return "Card types among spells you've cast this turn: " + types.size()
                + (types.size() > 0 ? " (" + String.join(", ", types) + ')' : "");
    }

    @Override
    public Hint copy() {
        return this;
    }
}

class AprilONeilHacktivistWatcher extends Watcher {

    private final Map<UUID, Set<CardType>> map = new HashMap<>();

    AprilONeilHacktivistWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell != null) {
            map.computeIfAbsent(spell.getControllerId(), x -> new HashSet<>()).addAll(spell.getCardType(game));
        }
    }

    @Override
    public void reset() {
        map.clear();
        super.reset();
    }

    static Set<CardType> getCardTypesCast(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(AprilONeilHacktivistWatcher.class)
                .map
                .getOrDefault(playerId, Collections.emptySet());
    }
}
