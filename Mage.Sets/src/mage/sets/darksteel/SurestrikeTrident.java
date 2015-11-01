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
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;

/**
 *
 * @author AlumiuN
 */
public class SurestrikeTrident extends CardImpl {

    public SurestrikeTrident(UUID ownerId) {
        super(ownerId, 147, "Surestrike Trident", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "DST";
        this.subtype.add("Equipment");

        // Equipped creature has first strike and "{T}, Unattach Surestrike Trident: This creature deals damage equal to its power to target player."
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT));
        DynamicValue xValue = new SourcePermanentPowerCount();
        Effect effect = new DamageTargetEffect(xValue);
        effect.setText("This creature deals damage equal to its power to target player");
        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        gainedAbility.addTarget(new TargetPlayer());
        gainedAbility.addCost(new SurestrikeTridentUnattachCost(getName(), getId()));
        effect = new GainAbilityAttachedEffect(gainedAbility, AttachmentType.EQUIPMENT);
        effect.setText("and \"{T}, Unattach {this}: This creature deals damage equal to its power to target player.\"");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.Benefit, new GenericManaCost(4)));
    }

    public SurestrikeTrident(final SurestrikeTrident card) {
        super(card);
    }

    @Override
    public SurestrikeTrident copy() {
        return new SurestrikeTrident(this);
    }
}

class SurestrikeTridentUnattachCost extends CostImpl {

    protected UUID sourceEquipmentId;

    public SurestrikeTridentUnattachCost(String name, UUID sourceId) {
        this.text = "Unattach " + name;
        this.sourceEquipmentId = sourceId;
    }

    public SurestrikeTridentUnattachCost(final SurestrikeTridentUnattachCost cost) {
        super(cost);
        this.sourceEquipmentId = cost.sourceEquipmentId;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent != null) {
            for (UUID attachmentId : permanent.getAttachments()) {
                Permanent attachment = game.getPermanent(attachmentId);
                if (attachment != null && attachment.getId().equals(sourceEquipmentId)) {
                    paid = permanent.removeAttachment(attachmentId, game);
                    if (paid) {
                        break;
                    }
                }
            }

        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent != null) {
            for (UUID attachmentId : permanent.getAttachments()) {
                Permanent attachment = game.getPermanent(attachmentId);
                if (attachment != null && attachment.getId().equals(sourceEquipmentId)) {
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public SurestrikeTridentUnattachCost copy() {
        return new SurestrikeTridentUnattachCost(this);
    }

}
