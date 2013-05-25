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
package mage.sets.morningtide;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.EvokeAbility;
import mage.cards.CardImpl;
import mage.target.Target;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author LevelX2
 */
public class Nevermaker extends CardImpl<Nevermaker> {

    public Nevermaker(UUID ownerId) {
        super(ownerId, 44, "Nevermaker", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "MOR";
        this.subtype.add("Elemental");

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Nevermaker leaves the battlefield, put target nonland permanent on top of its owner's library.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PutOnLibraryTargetEffect(true), false);
        Target target = new TargetNonlandPermanent();
        target.setRequired(true);
        ability.addTarget(target);
        this.addAbility(ability);

        // Evoke {3}{U}
        this.addAbility(new EvokeAbility(this, "{3}{U}"));
    }

    public Nevermaker(final Nevermaker card) {
        super(card);
    }

    @Override
    public Nevermaker copy() {
        return new Nevermaker(this);
    }
}
