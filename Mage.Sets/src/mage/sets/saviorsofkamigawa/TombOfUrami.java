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
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

/**
 *
 * @author Plopman
 */
public class TombOfUrami extends CardImpl<TombOfUrami> {

    public TombOfUrami(UUID ownerId) {
        super(ownerId, 165, "Tomb of Urami", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "SOK";
        this.supertype.add("Legendary");

        // {tap}: Add {B} to your mana pool. Tomb of Urami deals 1 damage to you if you don't control an Ogre.
        Ability ability = new BlackManaAbility();
        ability.addEffect(new DamageControllerEffect(1));
        this.addAbility(ability);
        // {2}{B}{B}, {tap}, Sacrifice all lands you control: Put a legendary 5/5 black Demon Spirit creature token with flying named Urami onto the battlefield.
        Ability ability2 = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new CreateTokenEffect(new UramiToken()), new ManaCostsImpl("{2}{B}{B}"));
        ability2.addCost(new TapSourceCost());
        ability2.addCost(new SacrificeAllLandCost());
        this.addAbility(ability2);
    }

    public TombOfUrami(final TombOfUrami card) {
        super(card);
    }

    @Override
    public TombOfUrami copy() {
        return new TombOfUrami(this);
    }
}


class SacrificeAllLandCost extends CostImpl<SacrificeAllLandCost> {

    public SacrificeAllLandCost() {
        this.text = "Sacrifice all lands you control";
    }

    public SacrificeAllLandCost(SacrificeAllLandCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        for(Permanent permanent : game.getBattlefield().getActivePermanents(new FilterControlledLandPermanent(), ability.getControllerId(), game)){
            paid |= permanent.sacrifice(sourceId, game);
        }
        return paid;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        if (!game.getPlayer(controllerId).canPaySacrificeCost()) {
            return false;
        }
        return true;
    }

    @Override
    public SacrificeAllLandCost copy() {
        return new SacrificeAllLandCost(this);
    }

}


class UramiToken extends Token {

    public UramiToken() {
        super("Urami", "legendary 5/5 black Demon Spirit creature token with flying named Urami");
        cardType.add(CardType.CREATURE);
        subtype.add("Demon");
        subtype.add("Spirit");
        supertype.add("Legendary");
        

        color = ObjectColor.BLACK;
        power = new MageInt(5);
        toughness = new MageInt(5);
        
        this.addAbility(FlyingAbility.getInstance());
    }
}