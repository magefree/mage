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
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToACreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MustBlockSourceTargetEffect;
import mage.abilities.effects.common.SkipNextUntapTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX
 */
public class MatsuTribeDecoy extends CardImpl<MatsuTribeDecoy> {

    public MatsuTribeDecoy(UUID ownerId) {
        super(ownerId, 227, "Matsu-Tribe Decoy", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Snake");
        this.subtype.add("Warrior");

        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {2}{G}: Target creature blocks Matsu-Tribe Decoy this turn if able.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MustBlockSourceTargetEffect(), new ManaCostsImpl("{2}{G}"));
	ability.addTarget(new TargetCreaturePermanent());
	this.addAbility(ability);
        // Whenever Kashi-Tribe Reaver deals combat damage to a creature, tap that creature and it doesn't untap during its controller's next untap step.
        Ability ability2;
        ability2 = new DealsCombatDamageToACreatureTriggeredAbility(new TapTargetEffect("that creature"), false, true);
        ability2.addEffect(new SkipNextUntapTargetEffect("and it"));
        this.addAbility(ability2);
    }

    public MatsuTribeDecoy(final MatsuTribeDecoy card) {
        super(card);
    }

    @Override
    public MatsuTribeDecoy copy() {
        return new MatsuTribeDecoy(this);
    }
}