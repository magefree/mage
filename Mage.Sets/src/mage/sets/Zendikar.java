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

import mage.sets.zendikar.*;
import mage.cards.ExpansionSet;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Zendikar extends ExpansionSet {

	private static final Zendikar fINSTANCE =  new Zendikar();

	public static Zendikar getInstance() {
		return fINSTANCE;
	}

	private Zendikar() {
		this.name = "Zendikar";
		this.cards.add(ArchiveTrap.class);
		this.cards.add(AridMesa.class);
		this.cards.add(BraveTheElements.class);
		this.cards.add(BurstLightning.class);
		this.cards.add(ConquerorsPledge.class);
		this.cards.add(DayOfJudgment.class);
		this.cards.add(EmeriaAngel.class);
		this.cards.add(GoblinRuinblaster.class);
		this.cards.add(KabiraCrossroads.class);
		this.cards.add(LotusCobra.class);
		this.cards.add(MarshFlats.class);
		this.cards.add(MistyRainforest.class);
		this.cards.add(OranRiefTheVastwood.class);
		this.cards.add(RampagingBaloths.class);
		this.cards.add(ScaldingTarn.class);
		this.cards.add(ScuteMob.class);
		this.cards.add(SteppeLynx.class);
		this.cards.add(SunspringExpedition.class);
		this.cards.add(TeeteringPeaks.class);
		this.cards.add(VerdantCatacombs.class);
	}

}
