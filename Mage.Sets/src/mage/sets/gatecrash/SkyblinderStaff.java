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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.continious.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class SkyblinderStaff extends CardImpl<SkyblinderStaff> {

    public SkyblinderStaff(UUID ownerId) {
        super(ownerId, 238, "Skyblinder Staff", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Equipment");

        // Equipped creature gets +1/+0 and can't be blocked by creatures with flying.
        Ability ability = new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostEquippedEffect(1, 0));
        ability.addEffect(new CantBeBlockedByCreaturesWithFlyingAttachedEffect());
        this.addAbility(ability);
        
        // Equip {3}
        this.addAbility(new EquipAbility(Constants.Outcome.BoostCreature, new GenericManaCost(3)));

    }

    public SkyblinderStaff(final SkyblinderStaff card) {
        super(card);
    }

    @Override
    public SkyblinderStaff copy() {
        return new SkyblinderStaff(this);
    }
}

class CantBeBlockedByCreaturesWithFlyingAttachedEffect extends RestrictionEffect<CantBeBlockedByCreaturesWithFlyingAttachedEffect> {

    public CantBeBlockedByCreaturesWithFlyingAttachedEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Equipped creature can't be blocked by creatures with flying";
    }

    public CantBeBlockedByCreaturesWithFlyingAttachedEffect(final CantBeBlockedByCreaturesWithFlyingAttachedEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        if (attachment != null && attachment.getAttachedTo() != null) {
            Permanent perm = game.getPermanent(attachment.getAttachedTo());
            if (perm != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        if (blocker.getAbilities().contains(FlyingAbility.getInstance())) {
            return false;
        }
        return true;
    }

    @Override
    public CantBeBlockedByCreaturesWithFlyingAttachedEffect copy() {
        return new CantBeBlockedByCreaturesWithFlyingAttachedEffect(this);
    }
}
