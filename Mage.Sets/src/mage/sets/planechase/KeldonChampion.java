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
package mage.sets.planechase;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.EchoAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public class KeldonChampion extends CardImpl<KeldonChampion> {

    public KeldonChampion(UUID ownerId) {
        super(ownerId, 58, "Keldon Champion", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.expansionSetCode = "HOP";
        this.subtype.add("Human");
        this.subtype.add("Barbarian");

        this.color.setRed(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.addAbility(HasteAbility.getInstance());
        // Echo {2}{R}{R}
        this.addAbility(new EchoAbility("{2}{R}{R}"));
        // When Keldon Champion enters the battlefield, it deals 3 damage to target player.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(3), false);
        ability.addTarget(new TargetPlayer(true));
        this.addAbility(ability);
    }

    public KeldonChampion(final KeldonChampion card) {
        super(card);
    }

    @Override
    public KeldonChampion copy() {
        return new KeldonChampion(this);
    }
}
