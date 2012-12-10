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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class RazorBoomerang extends CardImpl<RazorBoomerang> {

    public RazorBoomerang(UUID ownerId) {
        super(ownerId, 129, "Razor Boomerang", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Equipment");

        // Equipped creature has "{tap}, Unattach Razor Boomerang: Razor Boomerang deals 1 damage to target creature or player. Return Razor Boomerang to its owner's hand."
        Ability gainAbility = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new RazorBoomerangEffect(this.getId()), new TapSourceCost());
        gainAbility.addCost(new UnattachCost(this.getId()));
        gainAbility.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new GainAbilityAttachedEffect(gainAbility, Constants.AttachmentType.EQUIPMENT)));

        // Equip {2}
        this.addAbility(new EquipAbility(Constants.Outcome.AddAbility, new GenericManaCost(2)));
    }

    public RazorBoomerang(final RazorBoomerang card) {
        super(card);
    }

    @Override
    public RazorBoomerang copy() {
        return new RazorBoomerang(this);
    }
}

class UnattachCost extends CostImpl<UnattachCost> {

    private UUID attachmentid;

    public UnattachCost(UUID attachmentid) {
        this.text = "Unattach Razor Boomerang";
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

class RazorBoomerangEffect extends OneShotEffect<RazorBoomerangEffect> {

    private static String text = "Razor Boomerang deals 1 damage to target creature or player. Return Razor Boomerang to its owner's hand";
    private UUID attachmentid;

    RazorBoomerangEffect(UUID attachmentid) {
        super(Outcome.Damage);
        this.attachmentid = attachmentid;
        staticText = text;
    }

    RazorBoomerangEffect(RazorBoomerangEffect effect) {
        super(effect);
        this.attachmentid = effect.attachmentid;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID target : targetPointer.getTargets(game, source)) {
            Permanent creature = game.getPermanent(target);
            if (creature != null) {
                creature.damage(1, attachmentid, game, true, false);
            }
            Player player = game.getPlayer(target);
            if (player != null) {
                player.damage(1, attachmentid, game, false, true);
            }
        }
        Permanent razor = game.getPermanent(attachmentid);
        if (razor != null) {
            razor.moveToZone(Constants.Zone.HAND, id, game, true);
        }
        return true;
    }

    @Override
    public RazorBoomerangEffect copy() {
        return new RazorBoomerangEffect(this);
    }
}
