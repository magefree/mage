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
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.filter.Filter.ComparisonScope;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author LevelX
 */
public class NineRingedBo extends CardImpl<NineRingedBo> {

     private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("spirit");

    static {
        filter.getSubtype().add("Spirit");
        filter.setScopeSubtype(ComparisonScope.Any);
    }

    public NineRingedBo(UUID ownerId) {
        super(ownerId, 263, "Nine-Ringed Bo", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "CHK";

        // {T}: Nine-Ringed Bo deals 1 damage to target Spirit creature. If that creature would die this turn, exile it instead.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        // If that creature would die this turn, exile it instead.
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NineRingedBoEffect()));

        this.addWatcher(new DamagedByWatcher());

    }

    public NineRingedBo(final NineRingedBo card) {
        super(card);
    }

    @Override
    public NineRingedBo copy() {
        return new NineRingedBo(this);
    }
}

    class NineRingedBoEffect extends ReplacementEffectImpl<NineRingedBoEffect> {

    public NineRingedBoEffect() {
        super(Duration.EndOfTurn, Outcome.Exile);
        staticText = "If that creature would die this turn, exile it instead";
    }

    public NineRingedBoEffect(final NineRingedBoEffect effect) {
        super(effect);
    }

    @Override
    public NineRingedBoEffect copy() {
        return new NineRingedBoEffect(this);
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
        if (event.getType() == EventType.ZONE_CHANGE && ((ZoneChangeEvent)event).isDiesEvent()) {
                        DamagedByWatcher watcher = 
                                (DamagedByWatcher) game.getState().getWatchers().get("DamagedByWatcher", source.getSourceId());
                        if (watcher != null)
                                return watcher.damagedCreatures.contains(event.getTargetId());
                        }
        return false;
    }

}


