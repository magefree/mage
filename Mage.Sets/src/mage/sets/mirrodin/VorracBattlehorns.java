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
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author fireshoes
 */
public class VorracBattlehorns extends CardImpl {

    public VorracBattlehorns(UUID ownerId) {
        super(ownerId, 271, "Vorrac Battlehorns", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "MRD";
        this.subtype.add("Equipment");

        // Equipped creature has trample and can't be blocked by more than one creature.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.EQUIPMENT));
        Effect effect = new CantBeBlockedByMoreThanOneAttachedEffect(AttachmentType.EQUIPMENT, 1);
        effect.setText("and can't be blocked by more than one creature");
        ability.addEffect(effect);
        this.addAbility(ability);
        
        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1)));
    }

    public VorracBattlehorns(final VorracBattlehorns card) {
        super(card);
    }

    @java.lang.Override
    public VorracBattlehorns copy() {
        return new VorracBattlehorns(this);
    }
}

class CantBeBlockedByMoreThanOneAttachedEffect extends ContinuousEffectImpl {

    protected int amount;
    protected AttachmentType attachmentType;

    public CantBeBlockedByMoreThanOneAttachedEffect(AttachmentType attachmentType, int amount) {
        this(attachmentType, amount, Duration.WhileOnBattlefield);
    }

    public CantBeBlockedByMoreThanOneAttachedEffect(AttachmentType attachmentType, int amount, Duration duration) {
        super(duration, Outcome.Benefit);
        this.amount = amount;
        this.attachmentType = attachmentType;
        staticText = (attachmentType.equals(AttachmentType.AURA) ? "Enchanted" : "Equipped") + " creature can't be blocked by more than " + CardUtil.numberToText(amount) + " creature" + (amount==1 ?"":"s");
    }

    public CantBeBlockedByMoreThanOneAttachedEffect(final CantBeBlockedByMoreThanOneAttachedEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.attachmentType = effect.attachmentType;
    }

    @java.lang.Override
    public CantBeBlockedByMoreThanOneAttachedEffect copy() {
        return new CantBeBlockedByMoreThanOneAttachedEffect(this);
    }

    @java.lang.Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        switch (layer) {
            case RulesEffects:
                Permanent attachment = game.getPermanent(source.getSourceId());
                if (attachment != null && attachment.getAttachedTo() != null) {
                    Permanent perm = game.getPermanent(attachment.getAttachedTo());
                    if (perm != null) {
                        perm.setMaxBlockedBy(amount);
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    @java.lang.Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @java.lang.Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
