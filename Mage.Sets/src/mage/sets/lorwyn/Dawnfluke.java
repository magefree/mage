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
package mage.sets.lorwyn;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PreventDamageTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.EvokeAbility;
import mage.cards.CardImpl;
import mage.target.Target;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author LevelX2
 */
public class Dawnfluke extends CardImpl<Dawnfluke> {

    public Dawnfluke(UUID ownerId) {
        super(ownerId, 12, "Dawnfluke", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.expansionSetCode = "LRW";
        this.subtype.add("Elemental");

        this.color.setWhite(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // When Dawnfluke enters the battlefield, prevent the next 3 damage that would be dealt to target creature or player this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PreventDamageTargetEffect(Duration.EndOfTurn, 3));
        Target target = new TargetCreatureOrPlayer();
        target.setRequired(true);
        ability.addTarget(target);
        this.addAbility(ability);
        // Evoke {W}
        this.addAbility(new EvokeAbility(this, "{W}"));
    }

    public Dawnfluke(final Dawnfluke card) {
        super(card);
    }

    @Override
    public Dawnfluke copy() {
        return new Dawnfluke(this);
    }
}
