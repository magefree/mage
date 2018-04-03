/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
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
public class DampingSphere extends CardImpl {

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
        CastSpellLastTurnWatcher watcher = (CastSpellLastTurnWatcher) game.getState().getWatchers().get(CastSpellLastTurnWatcher.class.getSimpleName());
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
