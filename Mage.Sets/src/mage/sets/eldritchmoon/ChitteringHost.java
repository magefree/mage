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
package mage.sets.eldritchmoon;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardSetInfo;
import mage.cards.MeldCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class ChitteringHost extends MeldCard {
    // TODO: EJM - Remove this
    public ChitteringHost(UUID ownerId) {
        this(ownerId, new CardSetInfo("Chittering Host", "EMN", "96", Rarity.COMMON));
    }

    public ChitteringHost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add("Eldrazi");
        this.subtype.add("Horror");
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        this.nightCard = true; // Meld card

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Menace <i>(This creature can't be blocked except by two or more creatures.
        this.addAbility(new MenaceAbility());

        // When Chittering Host enters the battlefield, other creatures you control get +1/+0 and gain menace until end of turn.
        Effect effect = new BoostControlledEffect(1, 0, Duration.EndOfTurn, true);
        effect.setText("other creatures you control get +1/+0");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false);
        effect = new GainAbilityAllEffect(new MenaceAbility(), Duration.EndOfTurn, new FilterControlledCreaturePermanent("other creatures"), true);
        effect.setText("and gain menace until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public ChitteringHost(final ChitteringHost card) {
        super(card);
    }

    @Override
    public ChitteringHost copy() {
        return new ChitteringHost(this);
    }
}
