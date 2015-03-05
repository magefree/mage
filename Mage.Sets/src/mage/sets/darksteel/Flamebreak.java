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
package mage.sets.darksteel;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author LevelX2
 */
public class Flamebreak extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("creature without flying");

    static {
        filter1.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public Flamebreak(UUID ownerId) {
        super(ownerId, 61, "Flamebreak", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{R}{R}{R}");
        this.expansionSetCode = "DST";

        this.color.setRed(true);

        // Flamebreak deals 3 damage to each creature without flying and each player. Creatures dealt damage this way can't be regenerated this turn.
        this.getSpellAbility().addEffect(new DamageEverythingEffect(new StaticValue(3), filter1));
        this.getSpellAbility().addEffect(new FlamebreakCantRegenerateEffect());
        this.getSpellAbility().addWatcher(new DamagedByWatcher());        
    }

    public Flamebreak(final Flamebreak card) {
        super(card);
    }

    @Override
    public Flamebreak copy() {
        return new Flamebreak(this);
    }
}

class FlamebreakCantRegenerateEffect extends ContinuousRuleModifyingEffectImpl {

    public FlamebreakCantRegenerateEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        staticText = "Creatures dealt damage this way can't be regenerated this turn";
    }

    public FlamebreakCantRegenerateEffect(final FlamebreakCantRegenerateEffect effect) {
        super(effect);
    }

    @Override
    public FlamebreakCantRegenerateEffect copy() {
        return new FlamebreakCantRegenerateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.REGENERATE) {
            DamagedByWatcher watcher = (DamagedByWatcher) game.getState().getWatchers().get("DamagedByWatcher", source.getSourceId());
            if (watcher != null) {
                return watcher.wasDamaged(event.getTargetId(), game);
            }
        }
        return false;
    }

}
