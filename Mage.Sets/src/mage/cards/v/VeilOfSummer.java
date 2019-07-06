package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CantBeCounteredControlledEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.keyword.HexproofFromBlackAbility;
import mage.abilities.keyword.HexproofFromBlueAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.filter.StaticFilters;
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
public final class VeilOfSummer extends CardImpl {

    public VeilOfSummer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Draw a card if an opponent has cast a blue or black spell this turn. Spells you control can't be countered this turn. You and permanents you control gain hexproof from blue and from black until end of turn.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1),
                VeilOfSummerCondition.instance, "Draw a card " +
                "if an opponent has cast a blue or black spell this turn"
        ));
        this.getSpellAbility().addEffect(new CantBeCounteredControlledEffect(
                StaticFilters.FILTER_SPELL, Duration.EndOfTurn
        ).setText("Spells you control can't be countered this turn"));
        this.getSpellAbility().addEffect(new VeilOfSummerEffect());
        this.getSpellAbility().addWatcher(new VeilOfSummerWatcher());
    }

    private VeilOfSummer(final VeilOfSummer card) {
        super(card);
    }

    @Override
    public VeilOfSummer copy() {
        return new VeilOfSummer(this);
    }
}

enum VeilOfSummerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        VeilOfSummerWatcher watcher = game.getState().getWatcher(VeilOfSummerWatcher.class);
        return watcher != null && watcher.opponentCastBlueBlackSpell(source.getControllerId());
    }
}

class VeilOfSummerWatcher extends Watcher {

    private final Set<UUID> opponentsCastBlueBlackSpell = new HashSet();

    VeilOfSummerWatcher() {
        super(WatcherScope.GAME);
    }

    private VeilOfSummerWatcher(final VeilOfSummerWatcher watcher) {
        super(watcher);
        this.opponentsCastBlueBlackSpell.addAll(watcher.opponentsCastBlueBlackSpell);
    }

    @Override
    public VeilOfSummerWatcher copy() {
        return new VeilOfSummerWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (GameEvent.EventType.SPELL_CAST == event.getType()) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell == null) {
                return;
            }
            if (spell.getColor(game).isBlack() || spell.getColor(game).isBlue()) {
                opponentsCastBlueBlackSpell.addAll(game.getOpponents(spell.getControllerId()));
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        opponentsCastBlueBlackSpell.clear();
    }

    boolean opponentCastBlueBlackSpell(UUID playerId) {
        return opponentsCastBlueBlackSpell.contains(playerId);
    }
}

class VeilOfSummerEffect extends OneShotEffect {

    VeilOfSummerEffect() {
        super(Outcome.Benefit);
        staticText = "You and permanents you control gain hexproof from blue and from black until end of turn";
    }

    private VeilOfSummerEffect(final VeilOfSummerEffect effect) {
        super(effect);
    }

    @Override
    public VeilOfSummerEffect copy() {
        return new VeilOfSummerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addEffect(new GainAbilityControlledEffect(
                HexproofFromBlueAbility.getInstance(), Duration.EndOfTurn
        ), source);
        game.addEffect(new GainAbilityControlledEffect(
                HexproofFromBlackAbility.getInstance(), Duration.EndOfTurn
        ), source);
        game.addEffect(new GainAbilityControllerEffect(
                HexproofFromBlueAbility.getInstance(), Duration.EndOfTurn
        ), source);
        game.addEffect(new GainAbilityControllerEffect(
                HexproofFromBlackAbility.getInstance(), Duration.EndOfTurn
        ), source);
        return true;
    }
}