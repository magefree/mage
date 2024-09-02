package mage.cards.p;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;
import mage.game.stack.Spell;
import mage.util.CardUtil;
import mage.watchers.Watcher;

/**
 *
 * @author Grath
 */
public final class PanopticProjektor extends CardImpl {

    public PanopticProjektor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {T}: The next face-down creature spell you cast this turn costs {3} less to cast.
        this.addAbility(new SimpleActivatedAbility(
                new PanopticProjektorEffect(), new TapSourceCost()
        ), new PanopticProjektorWatcher());

        // If turning a face-down permanent face up causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new PanopticProjektorReplacementEffect()));
    }

    private PanopticProjektor(final PanopticProjektor card) {
        super(card);
    }

    @Override
    public PanopticProjektor copy() {
        return new PanopticProjektor(this);
    }
}

class PanopticProjektorEffect extends CostModificationEffectImpl {

    private int spellsCast;

    PanopticProjektorEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "the next face-down creature spell you cast this turn costs {3} less to cast";
    }

    private PanopticProjektorEffect(final PanopticProjektorEffect effect) {
        super(effect);
        this.spellsCast = effect.spellsCast;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        PanopticProjektorWatcher watcher = game.getState().getWatcher(PanopticProjektorWatcher.class);
        if (watcher != null) {
            spellsCast = watcher.getCount(source.getControllerId());
        }
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 3);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        PanopticProjektorWatcher watcher = game.getState().getWatcher(PanopticProjektorWatcher.class);
        if (watcher == null) {
            return false;
        }
        if (watcher.getCount(source.getControllerId()) > spellsCast) {
            discard(); // only one use
            return false;
        }
        if (!(abilityToModify instanceof SpellAbility)
                || !abilityToModify.isControlledBy(source.getControllerId())) {
            return false;
        }
        Card spellCard = ((SpellAbility) abilityToModify).getCharacteristics(game);
        return spellCard != null && ((SpellAbility) abilityToModify).getSpellAbilityCastMode().isFaceDown();
    }

    @Override
    public PanopticProjektorEffect copy() {
        return new PanopticProjektorEffect(this);
    }
}

class PanopticProjektorWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    PanopticProjektorWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getSourceId());
        if (spell != null && spell.isCreature(game) && spell.isFaceDown(game)) {
            playerMap.compute(event.getPlayerId(), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerMap.clear();
    }

    int getCount(UUID playerId) {
        return playerMap.getOrDefault(playerId, 0);
    }
}

class PanopticProjektorReplacementEffect extends ReplacementEffectImpl {

    PanopticProjektorReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a Wizard entering the battlefield under your control causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time";
    }

    private PanopticProjektorReplacementEffect(final PanopticProjektorReplacementEffect effect) {
        super(effect);
    }

    @Override
    public PanopticProjektorReplacementEffect copy() {
        return new PanopticProjektorReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event instanceof NumberOfTriggersEvent) {
            NumberOfTriggersEvent numberOfTriggersEvent = (NumberOfTriggersEvent) event;
            // Only triggers of the controller of Panoptic Projektor
            if (source.isControlledBy(event.getPlayerId())) {
                GameEvent sourceEvent = numberOfTriggersEvent.getSourceEvent();
                // Only turn face up triggers
                if (sourceEvent != null
                        && sourceEvent.getType() == GameEvent.EventType.TURNED_FACE_UP) {
                    // Only for triggers of permanents
                    return game.getPermanent(numberOfTriggersEvent.getSourceId()) != null;
                }
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}