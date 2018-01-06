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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public class SirenReaver extends CardImpl {

    public SirenReaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SIREN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // <i>Raid</i> — Siren Reaver costs {1} less to cast if you attacked with a creature this turn.
        Ability ability = new SimpleStaticAbility(Zone.STACK, new SpellCostReductionSourceEffect(1, RaidCondition.instance));
        ability.setAbilityWord(AbilityWord.RAID);
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

    }

    public SirenReaver(final SirenReaver card) {
        super(card);
    }

    @Override
    public SirenReaver copy() {
        return new SirenReaver(this);
    }
}
