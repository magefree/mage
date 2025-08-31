package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RhinoBarrelingBrute extends CardImpl {

    public RhinoBarrelingBrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(6);
        this.toughness = new MageInt(7);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Rhino attacks, if you've cast a spell with mana value 4 or greater this turn, draw a card.
        this.addAbility(new AttacksTriggeredAbility(new DrawCardSourceControllerEffect(1))
                .withInterveningIf(RhinoBarrelingBruteCondition.instance)
                .addHint(RhinoBarrelingBruteCondition.getHint()), new RhinoBarrelingBruteWatcher());
    }

    private RhinoBarrelingBrute(final RhinoBarrelingBrute card) {
        super(card);
    }

    @Override
    public RhinoBarrelingBrute copy() {
        return new RhinoBarrelingBrute(this);
    }
}

enum RhinoBarrelingBruteCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return RhinoBarrelingBruteWatcher.checkPlayer(game, source);
    }

    @Override
    public String toString() {
        return "you've cast a spell with mana value 4 or greater this turn";
    }
}

class RhinoBarrelingBruteWatcher extends Watcher {

    private final Set<UUID> set = new HashSet<>();

    RhinoBarrelingBruteWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell != null && spell.getManaValue() >= 4) {
            set.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        set.clear();
    }

    static boolean checkPlayer(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(RhinoBarrelingBruteWatcher.class)
                .set
                .contains(source.getControllerId());
    }
}
