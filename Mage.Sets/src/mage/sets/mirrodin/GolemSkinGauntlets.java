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

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author North
 */
public class GolemSkinGauntlets extends CardImpl {

    public GolemSkinGauntlets(UUID ownerId) {
        super(ownerId, 181, "Golem-Skin Gauntlets", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "MRD";
        this.subtype.add("Equipment");

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(new GolemSkinGauntletsAttachedCount(), new StaticValue(0), Duration.WhileOnBattlefield)));
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2)));
    }

    public GolemSkinGauntlets(final GolemSkinGauntlets card) {
        super(card);
    }

    @java.lang.Override
    public GolemSkinGauntlets copy() {
        return new GolemSkinGauntlets(this);
    }
}

// we can't use GolemSkinGauntletsAttachedCount
// compare to Goblin Gaveleer
class GolemSkinGauntletsAttachedCount implements DynamicValue {

    public GolemSkinGauntletsAttachedCount() {
    }

    public GolemSkinGauntletsAttachedCount(final GolemSkinGauntletsAttachedCount dynamicValue) {
    }

    @java.lang.Override
    public int calculate(Game game, Ability source, Effect effect) {
        int count = 0;
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null) {
            Permanent permanent = game.getPermanent(equipment.getAttachedTo());
            if (permanent != null) {
                List<UUID> attachments = permanent.getAttachments();
                for (UUID attachmentId : attachments) {
                    Permanent attached = game.getPermanent(attachmentId);
                    if (attached != null && attached.getSubtype(game).contains("Equipment")) {
                        count++;
                    }
                }
            }

        }
        return count;
    }

    @java.lang.Override
    public DynamicValue copy() {
        return new GolemSkinGauntletsAttachedCount(this);
    }

    @java.lang.Override
    public String toString() {
        return "1";
    }

    @java.lang.Override
    public String getMessage() {
        return "Equipment attached to it";
    }
}