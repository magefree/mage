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

import mage.constants.*;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public class LeoninBola extends CardImpl<LeoninBola> {

    public LeoninBola(UUID ownerId) {
        super(ownerId, 127, "Leonin Bola", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "DST";
        this.subtype.add("Equipment");

        // Equipped creature has "{tap}, Unattach Leonin Bola: Tap target creature."
        Ability gainAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new TapSourceCost());
        gainAbility.addCost(new UnattachCost(this.getId()));
        gainAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(gainAbility, AttachmentType.EQUIPMENT)));
        
        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1)));
    }

    public LeoninBola(final LeoninBola card) {
        super(card);
    }

    @Override
    public LeoninBola copy() {
        return new LeoninBola(this);
    }
}

class UnattachCost extends CostImpl<UnattachCost> {
    
    private UUID attachmentid;

    public UnattachCost(UUID attachmentid) {
        this.text = "Unattach Leonin Bola";
        this.attachmentid = attachmentid;
    }

    public UnattachCost(UnattachCost cost) {
        super(cost);
        this.attachmentid = cost.attachmentid;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent != null) {
                Permanent attachment = game.getPermanent(attachmentid);
                if (attachment != null) {
                    permanent.removeAttachment(attachmentid, game);
                    this.paid = true;
                }
            }
        return paid;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent != null) {
                Permanent attachment = game.getPermanent(attachmentid);
                if (attachment != null && permanent.getAttachments().contains(attachmentid)) {
                    return true;
                }
        }
        return false;
    }

    @Override
    public UnattachCost copy() {
        return new UnattachCost(this);
    }
}
