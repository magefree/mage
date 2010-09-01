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

package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.SearchLibraryPutInPlayEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.sets.ShardsOfAlara;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class JundPanorama extends CardImpl<JundPanorama> {

	public JundPanorama(UUID ownerId) {
		super(ownerId, "Jund Panorama", new CardType[]{CardType.LAND}, null);
		this.expansionSetId = ShardsOfAlara.getInstance().getId();
		this.addAbility(new ColorlessManaAbility());
		this.addAbility(new JundPanoramaAbility());
	}

	public JundPanorama(final JundPanorama card) {
		super(card);
	}

	@Override
	public JundPanorama copy() {
		return new JundPanorama(this);
	}

	@Override
	public String getArt() {
		return "116191_typ_reg_sty_010.jpg";
	}

}

class JundPanoramaAbility extends ActivatedAbilityImpl<JundPanoramaAbility> {

	public JundPanoramaAbility() {
		super(Zone.BATTLEFIELD, null);
		addCost(new TapSourceCost());
		addCost(new GenericManaCost(1));
		addCost(new SacrificeSourceCost());
		FilterCard filter = new FilterCard("Swamp, Mountain, or Forest");
		filter.getName().add("Swamp");
		filter.getName().add("Mountain");
		filter.getName().add("Forest");
		TargetCardInLibrary target = new TargetCardInLibrary(filter);
		addEffect(new SearchLibraryPutInPlayEffect(target, true, Outcome.PutLandInPlay));
	}

	public JundPanoramaAbility(final JundPanoramaAbility ability) {
		super(ability);
	}

	@Override
	public JundPanoramaAbility copy() {
		return new JundPanoramaAbility(this);
	}

	
}