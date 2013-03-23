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
package mage.sets.planechase2012;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.functions.ApplyToPermanent;

/**
 *
 * @author LevelX2
 */
public class SakashimasStudent extends CardImpl<SakashimasStudent> {

    public SakashimasStudent(UUID ownerId) {
        super(ownerId, 24, "Sakashima's Student", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.expansionSetCode = "PC2";
        this.subtype.add("Human");
        this.subtype.add("Ninja");

        this.color.setBlue(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Ninjutsu {1}{U}
        this.addAbility(new NinjutsuAbility(new ManaCostsImpl("{1}{U}")));
        // You may have Sakashima's Student enter the battlefield as a copy of any creature on the battlefield, except it's still a Ninja in addition to its other creature types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,new EntersBattlefieldEffect(
                new CopyPermanentEffect(new SakashimasStudentApplyToPermanent()),
                "You may have {this} enter the battlefield as a copy of any creature on the battlefield, except it's still a Ninja in addition to its other creature types",
                true)));

    }

    public SakashimasStudent(final SakashimasStudent card) {
        super(card);
    }

    @Override
    public SakashimasStudent copy() {
        return new SakashimasStudent(this);
    }
}

class SakashimasStudentApplyToPermanent extends ApplyToPermanent {

    @Override
    public Boolean apply(Game game, Permanent permanent) {
        if (!permanent.getSubtype().contains("Ninja")) {
            permanent.getSubtype().add("Ninja");
        }
        return true;
    }
}
