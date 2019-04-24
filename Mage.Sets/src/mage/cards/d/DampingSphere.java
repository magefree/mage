
package mage.cards.d;

import java.util.UUID;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.cost.SpellsCostIncreasementAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ManaEvent;
import mage.util.CardUtil;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 *
 * @author L_J
 */
public final class DampingSphere extends CardImpl {

    public DampingSphere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // If a land is tapped for two or more mana, it produces {C} instead of any other type and amount.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DampingSphereReplacementEffect()));

        // Each spell a player casts costs {1} more to cast for each other spell that player has cast this turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DampingSphereIncreasementAllEffect()), new CastSpellLastTurnWatcher());
    }

    public DampingSphere(final DampingSphere card) {
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

    DampingSphereReplacementEffect(final DampingSphereReplacementEffect effect) {
        super(effect);
    }

    @Override
    public DampingSphereReplacementEffect copy() {
        return new DampingSphereReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
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
        return event.getType() == EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObject mageObject = game.getObject(event.getSourceId());
        ManaEvent manaEvent = (ManaEvent) event;
        Mana mana = manaEvent.getMana();
        return mageObject != null && mageObject.isLand() && mana.count() > 1;
    }
}

class DampingSphereIncreasementAllEffect extends SpellsCostIncreasementAllEffect {

    DampingSphereIncreasementAllEffect() {
        super(0);
        this.staticText = "Each spell a player casts costs {1} more to cast for each other spell that player has cast this turn";
    }

    DampingSphereIncreasementAllEffect(DampingSphereIncreasementAllEffect effect) {
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
