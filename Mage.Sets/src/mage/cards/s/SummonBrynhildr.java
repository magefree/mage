package mage.cards.s;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.delayed.CastNextSpellDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class SummonBrynhildr extends CardImpl {

    public SummonBrynhildr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Chain -- Exile the top card of your library. During any turn you put a lore counter on this Saga, you may play that card.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, ability -> {
            ability.addEffect(new SummonBrynhildrExileEffect());
            ability.withFlavorWord("Chain");
        });

        // II, III -- Gestalt Mode -- When you next cast a creature spell this turn, it gains haste until end of turn.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_III, ability -> {
            ability.addEffect(new CreateDelayedTriggeredAbilityEffect(
                    new CastNextSpellDelayedTriggeredAbility(
                            new SummonBrynhildrHasteEffect(), StaticFilters.FILTER_SPELL_A_CREATURE, true
                    )
            ));
            ability.withFlavorWord("Gestalt Mode");
        });
        this.addAbility(sagaAbility.addHint(SummonBrynhildrCondition.getHint()), new SummonBrynhildrWatcher());
    }

    private SummonBrynhildr(final SummonBrynhildr card) {
        super(card);
    }

    @Override
    public SummonBrynhildr copy() {
        return new SummonBrynhildr(this);
    }
}

class SummonBrynhildrExileEffect extends OneShotEffect {

    SummonBrynhildrExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of your library. During any turn you " +
                "put a lore counter on this Saga, you may play that card";
    }

    private SummonBrynhildrExileEffect(final SummonBrynhildrExileEffect effect) {
        super(effect);
    }

    @Override
    public SummonBrynhildrExileEffect copy() {
        return new SummonBrynhildrExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        CardUtil.makeCardPlayable(
                game, source, card, false, Duration.Custom, false,
                source.getControllerId(), SummonBrynhildrCondition.instance
        );
        return true;
    }
}

enum SummonBrynhildrCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return SummonBrynhildrWatcher.check(game, source);
    }

    @Override
    public String toString() {
        return "You put a lore counter on this permanent this turn";
    }
}

class SummonBrynhildrWatcher extends Watcher {

    private final Map<MageObjectReference, Set<UUID>> map = new HashMap<>();

    SummonBrynhildrWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.COUNTER_ADDED
                || !CounterType.STUN.getName().equals(event.getData())) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            map.computeIfAbsent(new MageObjectReference(permanent, game), x -> new HashSet<>())
                    .add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    static boolean check(Game game, Ability source) {
        return game.getState()
                .getWatcher(SummonBrynhildrWatcher.class)
                .map
                .getOrDefault(new MageObjectReference(
                        source.getSourceId(), source.getStackMomentSourceZCC(), game
                ), Collections.emptySet())
                .contains(source.getControllerId());
    }
}

class SummonBrynhildrHasteEffect extends OneShotEffect {

    SummonBrynhildrHasteEffect() {
        super(Outcome.Benefit);
        staticText = "it gains haste until end of turn";
    }

    private SummonBrynhildrHasteEffect(final SummonBrynhildrHasteEffect effect) {
        super(effect);
    }

    @Override
    public SummonBrynhildrHasteEffect copy() {
        return new SummonBrynhildrHasteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        if (spell != null) {
            game.getState().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                    .setTargetPointer(new FixedTarget(spell.getCard().getId())), source);
        }
        return true;
    }
}
