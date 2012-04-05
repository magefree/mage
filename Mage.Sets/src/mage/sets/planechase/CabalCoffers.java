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
package mage.sets.planechase;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Zone;
import mage.Constants.Rarity;
import mage.cards.CardImpl;
import mage.abilities.Ability;
import mage.Mana;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ManaEffect;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;


/**
 *
 * @author anonymous
 */
public class CabalCoffers extends CardImpl<CabalCoffers> {
    
    private final static FilterControlledPermanent filter = new FilterControlledPermanent("Swamps you control");

    static {
        filter.getSubtype().add("Swamp");
    }

    public CabalCoffers(UUID ownerId) {
        super(ownerId, 132, "Cabal Coffers", Rarity.UNCOMMON, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "HOP";

        // {2}, {tap}: Add {B} to your mana pool for each Swamp you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CabalCoffersManaEffect(), new TapSourceCost());
        ability.addCost(new GenericManaCost(2));
        this.addAbility(ability);
    }

    public CabalCoffers(final CabalCoffers card) {
        super(card);
    }

    @Override
    public CabalCoffers copy() {
        return new CabalCoffers(this);
    }

}

class CabalCoffersManaEffect extends ManaEffect<CabalCoffersManaEffect> {
    
    private final static FilterControlledPermanent filter = new FilterControlledPermanent("Swamps you control");

    static {
        filter.getSubtype().add("Swamp");
    }
    
    CabalCoffersManaEffect() {
        super();
        staticText = "Add B to your mana pool for each Swamp you control";
    }
    
    CabalCoffersManaEffect(final CabalCoffersManaEffect effect) {
        super(effect);
    }
    
    @Override
    public CabalCoffersManaEffect copy() {
        return new CabalCoffersManaEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
	int swamps = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game).size();
	if (source.getSourceId() != null) {
		game.getPlayer(source.getControllerId()).getManaPool().addMana(Mana.BlackMana(swamps), game, source);
	}
	return true;
    }
}