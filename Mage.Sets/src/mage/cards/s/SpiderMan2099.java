package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ruleModifying.CantCastDuringFirstThreeTurnsEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.common.TargetAnyTarget;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpiderMan2099 extends CardImpl {

    public SpiderMan2099(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // From the Future -- You can't cast Spider-Man 2099 during your first, second, or third turns of the game.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new CantCastDuringFirstThreeTurnsEffect("{this}")
        ).withFlavorWord("From the Future"));

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // At the beginning of your end step, if you've played a land or cast a spell this turn from anywhere other than your hand, Spider-Man 2099 deals damage equal to his power to any target.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new DamageTargetEffect(SourcePermanentPowerValue.NOT_NEGATIVE)
                        .setText("{this} deals damage equal to his power to any target")
        ).withInterveningIf(SpiderMan2099Condition.instance);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability.addHint(SpiderMan2099Condition.getHint()), new SpiderMan2099Watcher());
    }

    private SpiderMan2099(final SpiderMan2099 card) {
        super(card);
    }

    @Override
    public SpiderMan2099 copy() {
        return new SpiderMan2099(this);
    }
}

enum SpiderMan2099Condition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return SpiderMan2099Watcher.checkPlayer(game, source);
    }

    @Override
    public String toString() {
        return "you've played a land or cast a spell this turn from anywhere other than your hand";
    }
}

class SpiderMan2099Watcher extends Watcher {

    private final Set<UUID> set = new HashSet<>();

    SpiderMan2099Watcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case LAND_PLAYED:
                if (!Zone.HAND.match(event.getZone())) {
                    set.add(event.getPlayerId());
                }
                break;
            case SPELL_CAST:
                Spell spell = game.getSpell(event.getTargetId());
                if (spell != null && !Zone.HAND.match(spell.getFromZone())) {
                    set.add(spell.getControllerId());
                }
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
                .getWatcher(SpiderMan2099Watcher.class)
                .set
                .contains(source.getControllerId());
    }
}
