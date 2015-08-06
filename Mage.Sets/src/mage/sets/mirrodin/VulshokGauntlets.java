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
package mage.sets.mirrodin;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author fireshoes
 */
public class VulshokGauntlets extends CardImpl {

    public VulshokGauntlets(UUID ownerId) {
        super(ownerId, 273, "Vulshok Gauntlets", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "MRD";
        this.subtype.add("Equipment");

        // Equipped creature gets +4/+2 and doesn't untap during its controller's untap step.
        Effect effect = new BoostEquippedEffect(4, 2);
        effect.setText("Equipped creature gets +4/+2");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new VulshokGauntletsEffect();
        effect.setText("and has doesn't untap during its controller's untap step");
        ability.addEffect(effect);
        this.addAbility(ability);
        
        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(3)));
    }

    public VulshokGauntlets(final VulshokGauntlets card) {
        super(card);
    }

    @java.lang.Override
    public VulshokGauntlets copy() {
        return new VulshokGauntlets(this);
    }
}

class VulshokGauntletsEffect extends ReplacementEffectImpl {

    public VulshokGauntletsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Equipped creature doesn't untap during its controller's untap step";
    }

    public VulshokGauntletsEffect(final VulshokGauntletsEffect effect) {
        super(effect);
    }

    @java.lang.Override
    public VulshokGauntletsEffect copy() {
        return new VulshokGauntletsEffect(this);
    }

    @java.lang.Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @java.lang.Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP;
    }
    
    @java.lang.Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getTurn().getStepType() == PhaseStep.UNTAP) {
            Permanent equipment = game.getPermanent(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null) {
                Permanent equipped = game.getPermanent(equipment.getAttachedTo());
                if (equipped.getId().equals(event.getTargetId())) {
                    return true;
                }
            }
        }
        return false;
    }
}