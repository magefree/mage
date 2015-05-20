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
package mage.sets.alliances;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author LevelX2
 */
public class LordOfTresserhorn extends CardImpl {

    public LordOfTresserhorn(UUID ownerId) {
        super(ownerId, 193, "Lord of Tresserhorn", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");
        this.expansionSetCode = "ALL";
        this.supertype.add("Legendary");
        this.subtype.add("Zombie");

        this.power = new MageInt(10);
        this.toughness = new MageInt(4);

        // When Lord of Tresserhorn enters the battlefield, you lose 2 life, you sacrifice two creatures, and target opponent draws two cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseLifeSourceControllerEffect(2), false);
        ability.addEffect(new SacrificeTargetEffect(", you sacrifice two creatures"));
        Target target = new TargetControlledCreaturePermanent(2,2, new FilterControlledCreaturePermanent(), true);
        ability.addTarget(target);
        Effect effect = new DrawCardTargetEffect(2);
        effect.setText(", and target opponent draws two cards");
        effect.setTargetPointer(new SecondTargetPointer());
        ability.addEffect(effect);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
        // {B}: Regenerate Lord of Tresserhorn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl("{B}")));
    }

    public LordOfTresserhorn(final LordOfTresserhorn card) {
        super(card);
    }

    @Override
    public LordOfTresserhorn copy() {
        return new LordOfTresserhorn(this);
    }
}
