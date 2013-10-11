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
package mage.sets.theros;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ScryEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author LevelX2
 */
public class RageOfPurphoros extends CardImpl<RageOfPurphoros> {

    public RageOfPurphoros(UUID ownerId) {
        super(ownerId, 137, "Rage of Purphoros", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{4}{R}");
        this.expansionSetCode = "THS";

        this.color.setRed(true);

        // Rage of Purphoros deals 4 damage to target creature. It can't be regenerated this turn. Scry 1.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(true));
        this.getSpellAbility().addEffect(new RageOfPurphorosEffect());
        this.getSpellAbility().addEffect(new ScryEffect(1));

        this.addWatcher(new DamagedByWatcher());
    }

    public RageOfPurphoros(final RageOfPurphoros card) {
        super(card);
    }

    @Override
    public RageOfPurphoros copy() {
        return new RageOfPurphoros(this);
    }
}

class RageOfPurphorosEffect extends ReplacementEffectImpl<RageOfPurphorosEffect> {

    public RageOfPurphorosEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        staticText = "It can't be regenerated this turn";
    }

    public RageOfPurphorosEffect(final RageOfPurphorosEffect effect) {
        super(effect);
    }

    @Override
    public RageOfPurphorosEffect copy() {
        return new RageOfPurphorosEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.REGENERATE) {
            DamagedByWatcher watcher = (DamagedByWatcher) game.getState().getWatchers().get("DamagedByWatcher", source.getSourceId());
            if (watcher != null) {
                return watcher.damagedCreatures.contains(event.getTargetId());
            }
        }
        return false;
    }

}
