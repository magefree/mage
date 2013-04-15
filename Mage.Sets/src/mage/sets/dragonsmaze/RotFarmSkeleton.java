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
package mage.sets.dragonsmaze;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.costs.common.PutTopCardOfYourLibraryToGraveyardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.cards.CardImpl;

/**
 *
 * @author LevelX2
 */


public class RotFarmSkeleton extends CardImpl<RotFarmSkeleton> {

    public RotFarmSkeleton (UUID ownerId) {
        super(ownerId, 98, "Rot Farm Skeleton", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");
        this.expansionSetCode = "DGM";
        this.subtype.add("Plant");
        this.subtype.add("Skeleton");
        this.color.setBlack(true);
        this.color.setGreen(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Rot Farm Skeleton can't block.
        this.addAbility(new CantBlockAbility());
        // 2{B}{G}, Put the top four cards of your library into your graveyard: Return Rot Farm Skeleton from your graveyard to the battlefield. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(), new ManaCostsImpl("{2}{B}{G}"));
        ability.addCost(new PutTopCardOfYourLibraryToGraveyardCost(4));
        this.addAbility(ability);

    }

    public RotFarmSkeleton (final RotFarmSkeleton card) {
        super(card);
    }

    @Override
    public RotFarmSkeleton copy() {
        return new RotFarmSkeleton(this);
    }

}
