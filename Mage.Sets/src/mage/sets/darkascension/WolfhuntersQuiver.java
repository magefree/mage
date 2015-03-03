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
package mage.sets.darkascension;

import java.util.UUID;
import mage.abilities.Ability;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.Target;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class WolfhuntersQuiver extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Werewolf creature");

    static {
        filter.add(new SubtypePredicate("Werewolf"));
    }

    public WolfhuntersQuiver(UUID ownerId) {
        super(ownerId, 154, "Wolfhunter's Quiver", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "DKA";
        this.subtype.add("Equipment");

        // Equipped creature has "{tap}: This creature deals 1 damage to target creature or player"
        Ability abilityToGain = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new TapSourceCost());
        abilityToGain.addTarget(new TargetCreatureOrPlayer());
        Effect effect = new GainAbilityAttachedEffect(abilityToGain, AttachmentType.EQUIPMENT);
        effect.setText("Equipped creature has \"{tap}: This creature deals 1 damage to target creature or player\"");
        SimpleStaticAbility ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        
        // and "{T}: This creature deals 3 damage to target Werewolf creature."
        abilityToGain = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(3), new TapSourceCost());
        abilityToGain.addTarget(new TargetCreaturePermanent(filter));
        effect = new GainAbilityAttachedEffect(abilityToGain, AttachmentType.EQUIPMENT);
        effect.setText("and \"{T}: This creature deals 3 damage to target Werewolf creature");
        ability.addEffect(effect);
        this.addAbility(ability);
        
        // Equip {5}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(5)));
    }

    public WolfhuntersQuiver(final WolfhuntersQuiver card) {
        super(card);
    }

    @Override
    public WolfhuntersQuiver copy() {
        return new WolfhuntersQuiver(this);
    }
}
