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
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author Plopman
 */
public class AngerOfTheGods extends CardImpl<AngerOfTheGods> {

    public AngerOfTheGods(UUID ownerId) {
        super(ownerId, 112, "Anger of the Gods", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");
        this.expansionSetCode = "THS";

        this.color.setRed(true);

        // Anger of the Gods deals 3 damage to each creature. 
        this.getSpellAbility().addEffect(new DamageAllEffect(3, new FilterCreaturePermanent()));
        
        //If a creature dealt damage this way would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new AngerOfTheGodsEffect());
        this.addWatcher(new DamagedByWatcher());
    }

    public AngerOfTheGods(final AngerOfTheGods card) {
        super(card);
    }

    @Override
    public AngerOfTheGods copy() {
        return new AngerOfTheGods(this);
    }
}


class AngerOfTheGodsEffect extends ReplacementEffectImpl<AngerOfTheGodsEffect> {

        public AngerOfTheGodsEffect() {
                super(Duration.EndOfTurn, Outcome.Exile);
                staticText = "If a creature dealt damage this way would die this turn, exile it instead";
        }

        public AngerOfTheGodsEffect(final AngerOfTheGodsEffect effect) {
                super(effect);
        }

        @Override
        public AngerOfTheGodsEffect copy() {
                return new AngerOfTheGodsEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
                return true;
        }

        @Override
        public boolean replaceEvent(GameEvent event, Ability source, Game game) {
                Permanent permanent = ((ZoneChangeEvent)event).getTarget();
                if (permanent != null) {
                    return permanent.moveToExile(null, "", source.getId(), game);
                }
                return false;
        }

        @Override
        public boolean applies(GameEvent event, Ability source, Game game) {
                if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent)event).isDiesEvent()) {
                        DamagedByWatcher watcher =
                                (DamagedByWatcher) game.getState().getWatchers().get("DamagedByWatcher", source.getSourceId());
                        if (watcher != null){
                                return watcher.damagedCreatures.contains(event.getTargetId());
                        }
                }
                return false;
        }

} 