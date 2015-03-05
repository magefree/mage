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
package mage.sets.fatereforged;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 *
 * @author fireshoes
 */
public class HewedStoneRetainers extends CardImpl {

    public HewedStoneRetainers(UUID ownerId) {
        super(ownerId, 161, "Hewed Stone Retainers", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.expansionSetCode = "FRF";
        this.subtype.add("Golem");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Cast Hewed Stone Retainers only if you've cast another spell this turn.
       this.addAbility(new SimpleStaticAbility(Zone.ALL, new HewedStoneRetainersEffect()));
    }

    public HewedStoneRetainers(final HewedStoneRetainers card) {
        super(card);
    }

    @Override
    public HewedStoneRetainers copy() {
        return new HewedStoneRetainers(this);
    }
}

class HewedStoneRetainersEffect extends ContinuousRuleModifyingEffectImpl {
    HewedStoneRetainersEffect() {
       super(Duration.EndOfGame, Outcome.Detriment);
       staticText = "Cast {this} only if you've cast another spell this turn";
    }

    HewedStoneRetainersEffect(final HewedStoneRetainersEffect effect) {
       super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
       if (event.getSourceId().equals(source.getSourceId())) {
           CastSpellLastTurnWatcher watcher = (CastSpellLastTurnWatcher) game.getState().getWatchers().get("CastSpellLastTurnWatcher");
           if (watcher != null && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(source.getControllerId()) == 0) {
               return true;
           }
       }
       return false;

    }

    @Override
    public boolean apply(Game game, Ability source) {
       return true;
    }

    @Override
    public HewedStoneRetainersEffect copy() {
       return new HewedStoneRetainersEffect(this);
    }
}