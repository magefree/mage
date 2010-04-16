/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.sets;

import mage.sets.magic2010.*;
import mage.cards.ExpansionSet;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Magic2010 extends ExpansionSet {

	private static final Magic2010 fINSTANCE =  new Magic2010();

	public static Magic2010 getInstance() {
		return fINSTANCE;
	}

	private Magic2010() {
		this.name = "Magic 2010";
		this.cards.add(BallLightning.class);
		this.cards.add(BaneslayerAngel.class);
		this.cards.add(BirdsOfParadise.class);
		this.cards.add(Cancel.class);
		this.cards.add(CelestialPurge.class);
		this.cards.add(DragonskullSummit.class);
		this.cards.add(Earthquake.class);
		this.cards.add(EliteVanguard.class);
		this.cards.add(Flashfreeze.class);
		this.cards.add(GargoyleCastle.class);
		this.cards.add(GarrukWildspeaker.class);
		this.cards.add(GlacialFortress.class);
		this.cards.add(GreatSableStag.class);
		this.cards.add(HonorOfThePure.class);
		this.cards.add(HowlingMine.class);
		this.cards.add(JaceBeleren.class);
		this.cards.add(LightningBolt.class);
		this.cards.add(MasterOfTheWildHunt.class);
		this.cards.add(RootboundCrag.class);
		this.cards.add(SafePassage.class);
		this.cards.add(SunpetalGrove.class);
		this.cards.add(TerramorphicExpanse.class);
		this.cards.add(TimeWarp.class);
		this.cards.add(WhiteKnight.class);
	}
}
