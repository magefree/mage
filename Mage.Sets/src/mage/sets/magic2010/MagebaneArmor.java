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
package mage.sets.magic2010;

import java.util.LinkedList;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.continious.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class MagebaneArmor extends CardImpl<MagebaneArmor> {

    public MagebaneArmor(UUID ownerId) {
        super(ownerId, 214, "Magebane Armor", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "M10";
        this.subtype.add("Equipment");

        // Equipped creature gets +2/+4 and loses flying.
        this.addAbility(new MagebaneArmorAbility());
        // Prevent all noncombat damage that would be dealt to equipped creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MagebaneArmorPreventionEffect()));
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2)));
    }

    public MagebaneArmor(final MagebaneArmor card) {
        super(card);
    }

    @Override
    public MagebaneArmor copy() {
        return new MagebaneArmor(this);
    }
}

class MagebaneArmorAbility extends StaticAbility<MagebaneArmorAbility> {

    public MagebaneArmorAbility() {
        super(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 4));
        this.addEffect(new MagebaneArmorEffect());
    }

    public MagebaneArmorAbility(MagebaneArmorAbility ability) {
        super(ability);
    }

    @Override
    public MagebaneArmorAbility copy() {
        return new MagebaneArmorAbility(this);
    }

    @Override
    public String getRule() {
        return "Equipped creature gets +2/+4 and loses flying.";
    }
}

class MagebaneArmorEffect extends ContinuousEffectImpl {

    public MagebaneArmorEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
    }

    public MagebaneArmorEffect(final MagebaneArmorEffect effect) {
        super(effect);
    }

    @Override
    public MagebaneArmorEffect copy() {
        return new MagebaneArmorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent permanent = game.getPermanent(equipment.getAttachedTo());
            if (permanent != null) {
                LinkedList<Ability> abilities = new LinkedList(permanent.getAbilities());
                for (Ability ability : abilities) {
                    if (ability == FlyingAbility.getInstance()) {
                        permanent.getAbilities().remove(ability);
                        result = true;
                    }
                }
            }
        }
        return result;
    }
}

class MagebaneArmorPreventionEffect extends PreventionEffectImpl<MagebaneArmorPreventionEffect> {

    public MagebaneArmorPreventionEffect() {
        super(Duration.WhileOnBattlefield);
        this.staticText = "Prevent all noncombat damage that would be dealt to equipped creature";
    }

    public MagebaneArmorPreventionEffect(final MagebaneArmorPreventionEffect effect) {
        super(effect);
    }

    @Override
    public MagebaneArmorPreventionEffect copy() {
        return new MagebaneArmorPreventionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, equipment.getAttachedTo(), source.getId(), source.getControllerId(), event.getAmount(), false);
            if (!game.replaceEvent(preventEvent)) {
                int damage = event.getAmount();
                event.setAmount(0);
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, equipment.getAttachedTo(), source.getId(), source.getControllerId(), damage));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && !((DamageEvent) event).isCombatDamage()) {
            Permanent equipment = game.getPermanent(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null
                    && event.getTargetId().equals(equipment.getAttachedTo())) {
                return true;
            }
        }
        return false;
    }
}
