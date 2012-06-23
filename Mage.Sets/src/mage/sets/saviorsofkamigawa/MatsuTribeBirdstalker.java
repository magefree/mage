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
package mage.sets.saviorsofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToACreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SkipNextUntapTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;

/**
 *
 * @author LevelX
 */
public class MatsuTribeBirdstalker extends CardImpl<MatsuTribeBirdstalker> {

    public MatsuTribeBirdstalker(UUID ownerId) {
        super(ownerId, 137, "Matsu-Tribe Birdstalker", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.expansionSetCode = "SOK";
        this.subtype.add("Snake");
        this.subtype.add("Warrior");

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Kashi-Tribe Elite deals combat damage to a creature, tap that creature and it doesn't untap during its controller's next untap step.
        Ability ability;
        ability = new DealsCombatDamageToACreatureTriggeredAbility(new TapTargetEffect("that creature"), false, true);
        ability.addEffect(new SkipNextUntapTargetEffect("and it"));
        this.addAbility(ability);

        // {G}: Matsu-Tribe Birdstalker gains reach until end of turn. (It can block creatures with flying.)
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(ReachAbility.getInstance(), Constants.Duration.EndOfTurn),
                new ManaCostsImpl("{G}")));
    }

    public MatsuTribeBirdstalker(final MatsuTribeBirdstalker card) {
        super(card);
    }

    @Override
    public MatsuTribeBirdstalker copy() {
        return new MatsuTribeBirdstalker(this);
    }
}