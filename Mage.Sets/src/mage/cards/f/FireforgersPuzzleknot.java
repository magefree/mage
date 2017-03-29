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
package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCreatureOrPlayer;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public class FireforgersPuzzleknot extends CardImpl {

    public FireforgersPuzzleknot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // When Fireforger's Puzzleknot enters the battlefield, it deals 1 damage to target creature or player.
        Effect effect = new DamageTargetEffect(1);
        effect.setText("it deals 1 damage to target creature or player");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect);
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);

        // {2}{R}, Sacrifice Fireforger's Puzzleknot: It deals 1 damage to target creature or player.
        effect.setText("It deals 1 damage to target creature or player");
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{2}{R}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
    }

    public FireforgersPuzzleknot(final FireforgersPuzzleknot card) {
        super(card);
    }

    @Override
    public FireforgersPuzzleknot copy() {
        return new FireforgersPuzzleknot(this);
    }
}
