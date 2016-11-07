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
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetSpellOrPermanent;

/**
 *
 * @author LevelX2
 */
public class EightAndAHalfTails extends CardImpl {

    public EightAndAHalfTails(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.supertype.add("Legendary");
        this.subtype.add("Fox");
        this.subtype.add("Cleric");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{W}: Target permanent you control gains protection from white until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(ProtectionAbility.from(ObjectColor.WHITE), Duration.EndOfTurn), new ManaCostsImpl("{1}{W}"));
        Target target = new TargetControlledPermanent();
        ability.addTarget(target);
        this.addAbility(ability);
        // {1}: Target spell or permanent becomes white until end of turn.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BecomesColorTargetEffect(ObjectColor.WHITE, Duration.EndOfTurn, "Target spell or permanent becomes white until end of turn"), new ManaCostsImpl("{1}"));
        target = new TargetSpellOrPermanent();
        ability.addTarget(target);
        this.addAbility(ability);
    }

    public EightAndAHalfTails(final EightAndAHalfTails card) {
        super(card);
    }

    @Override
    public EightAndAHalfTails copy() {
        return new EightAndAHalfTails(this);
    }
}
