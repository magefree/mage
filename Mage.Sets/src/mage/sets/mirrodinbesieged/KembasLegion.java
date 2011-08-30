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
package mage.sets.mirrodinbesieged;

import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class KembasLegion extends CardImpl<KembasLegion> {

    public KembasLegion(UUID ownerId) {
        super(ownerId, 9, "Kemba's Legion", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");
        this.expansionSetCode = "MBS";
        this.subtype.add("Cat");
        this.subtype.add("Soldier");

        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        this.addAbility(VigilanceAbility.getInstance());

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KembasLegionEffect()));
    }

    public KembasLegion(final KembasLegion card) {
        super(card);
    }

    @Override
    public KembasLegion copy() {
        return new KembasLegion(this);
    }
}

class KembasLegionEffect extends ContinuousEffectImpl<KembasLegionEffect> {

    public KembasLegionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "{this} can block an additional creature for each Equipment attached to Kemba's Legion";
    }

    public KembasLegionEffect(final KembasLegionEffect effect) {
        super(effect);
    }

    @Override
    public KembasLegionEffect copy() {
        return new KembasLegionEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && !permanent.getAttachments().isEmpty()) {
            if (layer.equals(Layer.RulesEffects)) {
                // maxBlocks = 0 equals to "can block any number of creatures"
                if (permanent.getMaxBlocks() > 0) {
                    List<UUID> attachments = permanent.getAttachments();
                    int count = 0;
                    for (UUID attachmentId : attachments) {
                        Permanent attachment = game.getPermanent(attachmentId);
                        if (attachment != null && attachment.hasSubtype("Equipment")) {
                            count++;
                        }
                    }
                    permanent.setMaxBlocks(permanent.getMaxBlocks() + count);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
