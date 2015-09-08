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
package mage.sets.vintagemasters;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.keyword.DethroneAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.functions.ApplyToPermanent;

/**
 *
 * @author LevelX2
 */
public class DacksDuplicate extends CardImpl {

    public DacksDuplicate(UUID ownerId) {
        super(ownerId, 248, "Dack's Duplicate", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");
        this.expansionSetCode = "VMA";
        this.subtype.add("Shapeshifter");

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Dack's Duplicate enter the battlefield as a copy of any creature on the battlefield except it gains haste and dethrone.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new EntersBattlefieldEffect(new CopyPermanentEffect(new DacksDuplicateApplyToPermanent()),
                        "You may have {this} enter the battlefield as a copy of any creature on the battlefield except it gains haste and dethrone",
                        true)));
    }

    public DacksDuplicate(final DacksDuplicate card) {
        super(card);
    }

    @Override
    public DacksDuplicate copy() {
        return new DacksDuplicate(this);
    }
}

class DacksDuplicateApplyToPermanent extends ApplyToPermanent {

    @Override
    public Boolean apply(Game game, Permanent permanent) {
        /**
         * 29/05/2014	The ability of Dack’s Duplicate doesn’t target the
         * creature.
         */
        permanent.addAbility(new DethroneAbility(), game);
        permanent.addAbility(HasteAbility.getInstance(), game);
        return true;
    }

    @Override
    public Boolean apply(Game game, MageObject mageObject) {
        mageObject.getAbilities().add(new DethroneAbility());
        mageObject.getAbilities().add(HasteAbility.getInstance());
        return true;
    }

}
