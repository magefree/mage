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
package mage.cards.t;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.AttachedCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.SacrificeEquippedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamagedCreatureEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;


/**
 *
 * @author Quercitron
 */
public class ThirstingAxe extends CardImpl {

    public ThirstingAxe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +4/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(4, 0)));

        // At the beginning of your end step, if equipped creature didn't deal combat damage to a creature this turn, sacrifice it.
        TriggeredAbility ability = new BeginningOfYourEndStepTriggeredAbility(new SacrificeEquippedEffect(), false);
        Condition condition = new CompoundCondition(
                AttachedCondition.instance,
                new InvertCondition(new EquippedDealtCombatDamageToCreatureCondition()));
        String triggeredAbilityText = "At the beginning of your end step, if equipped creature " +
            "didn't deal combat damage to a creature this turn, sacrifice it.";
        ConditionalTriggeredAbility sacrificeTriggeredAbility = new ConditionalTriggeredAbility(ability, condition, triggeredAbilityText);
        this.addAbility(sacrificeTriggeredAbility, new CombatDamageToCreatureWatcher());

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    public ThirstingAxe(final ThirstingAxe card) {
        super(card);
    }

    @Override
    public ThirstingAxe copy() {
        return new ThirstingAxe(this);
    }
}

class EquippedDealtCombatDamageToCreatureCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            CombatDamageToCreatureWatcher watcher =
                    (CombatDamageToCreatureWatcher) game.getState().getWatchers().get(CombatDamageToCreatureWatcher.BASIC_KEY);
            return watcher.dealtDamage(equipment.getAttachedTo(), equipment.getAttachedToZoneChangeCounter(), game);
        }
        return false;
    }

}

class CombatDamageToCreatureWatcher extends Watcher {

    // which objects dealt combat damage to creature during the turn
    public final Set<MageObjectReference> dealtCombatDamageToCreature;

    final static String BASIC_KEY = "CombatDamageToCreatureWatcher";

    public CombatDamageToCreatureWatcher() {
        super(BASIC_KEY, WatcherScope.GAME);
        dealtCombatDamageToCreature = new HashSet<>();
    }

    public CombatDamageToCreatureWatcher(final CombatDamageToCreatureWatcher watcher) {
        super(watcher);
        dealtCombatDamageToCreature = new HashSet<>(watcher.dealtCombatDamageToCreature);
    }

    @Override
    public CombatDamageToCreatureWatcher copy() {
        return new CombatDamageToCreatureWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_CREATURE) {
            if (((DamagedCreatureEvent) event).isCombatDamage()) {
                MageObjectReference damageSource = new MageObjectReference(event.getSourceId(), game);
                dealtCombatDamageToCreature.add(damageSource);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        dealtCombatDamageToCreature.clear();
    }

    public boolean dealtDamage(UUID objectId, int zoneChangeCounter, Game game) {
        MageObjectReference reference = new MageObjectReference(objectId, zoneChangeCounter, game);
        return dealtCombatDamageToCreature.contains(reference);
    }

}
