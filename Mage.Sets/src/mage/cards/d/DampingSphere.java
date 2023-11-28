package mage.cards.d;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaEvent;
import mage.game.events.TappedForManaEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 * @author L_J
 */
public final class DampingSphere extends CardImpl {

    public DampingSphere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // If a land is tapped for two or more mana, it produces {C} instead of any other type and amount.
        this.addAbility(new SimpleStaticAbility(new DampingSphereReplacementEffect()));

        // Each spell a player casts costs {1} more to cast for each other spell that player has cast this turn.
        this.addAbility(new SimpleStaticAbility(new DampingSphereIncreasementAllEffect()), new CastSpellLastTurnWatcher());
    }

    private DampingSphere(final DampingSphere card) {
        super(card);
    }

    @Override
    public DampingSphere copy() {
        return new DampingSphere(this);
    }
}

class DampingSphereReplacementEffect extends ReplacementEffectImpl {

    DampingSphereReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "If a land is tapped for two or more mana, it produces {C} instead of any other type and amount";
    }

    private DampingSphereReplacementEffect(final DampingSphereReplacementEffect effect) {
        super(effect);
    }

    @Override
    public DampingSphereReplacementEffect copy() {
        return new DampingSphereReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ManaEvent manaEvent = (ManaEvent) event;
        Mana mana = manaEvent.getMana();
        mana.setToMana(Mana.ColorlessMana(1));
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        TappedForManaEvent manaEvent = (TappedForManaEvent) event;
        Permanent land = manaEvent.getPermanent();
        Mana mana = manaEvent.getMana();
        return land != null && land.isLand(game) && mana.count() > 1;
    }
}

class DampingSphereIncreasementAllEffect extends SpellsCostIncreasingAllEffect {

    DampingSphereIncreasementAllEffect() {
        super(1, new FilterCard(), TargetController.ANY);
        this.staticText = "Each spell a player casts costs {1} more to cast for each other spell that player has cast this turn";
    }

    private DampingSphereIncreasementAllEffect(DampingSphereIncreasementAllEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if (watcher != null) {
            int additionalCost = watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(abilityToModify.getControllerId());
            CardUtil.increaseCost(abilityToModify, additionalCost);
            return true;
        }
        return false;
    }

    @Override
    public DampingSphereIncreasementAllEffect copy() {
        return new DampingSphereIncreasementAllEffect(this);
    }
}
