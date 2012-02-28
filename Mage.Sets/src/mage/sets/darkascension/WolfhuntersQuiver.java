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
import mage.Constants.AttachmentType;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.Target;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class WolfhuntersQuiver extends CardImpl<WolfhuntersQuiver> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Werewolf creature");

    static {
        filter.getSubtype().add("Werewolf");
    }

    public WolfhuntersQuiver(UUID ownerId) {
        super(ownerId, 154, "Wolfhunter's Quiver", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "DKA";
        this.subtype.add("Equipment");

        // Equipped creature has "{tap}: This creature deals 1 damage to target creature or player"
        WolfhuntersQuiverAbility ability = new WolfhuntersQuiverAbility(1, new TargetCreatureOrPlayer());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ability, AttachmentType.EQUIPMENT)));
        // and "{tap}: This creature deals 3 damage to target Werewolf creature."
        ability = new WolfhuntersQuiverAbility(3, new TargetCreaturePermanent(filter));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ability, AttachmentType.EQUIPMENT)));
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

class WolfhuntersQuiverAbility extends ActivatedAbilityImpl<WolfhuntersQuiverAbility> {

    private String ruleText;

    public WolfhuntersQuiverAbility(int amount, Target target) {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(amount), new TapSourceCost());
        this.addTarget(target);

        ruleText = "This creature deals " + amount + " damage to target " + target.getTargetName();
    }

    public WolfhuntersQuiverAbility(WolfhuntersQuiverAbility ability) {
        super(ability);
        this.ruleText = ability.ruleText;
    }

    @Override
    public WolfhuntersQuiverAbility copy() {
        return new WolfhuntersQuiverAbility(this);
    }

    @Override
    public String getRule() {
        return ruleText;
    }
}
