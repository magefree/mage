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
package mage.sets.nemesis;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AddManaOfAnyColorEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class OverlaidTerrain extends CardImpl {

    public OverlaidTerrain(UUID ownerId) {
        super(ownerId, 108, "Overlaid Terrain", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");
        this.expansionSetCode = "NMS";

        // As Overlaid Terrain enters the battlefield, sacrifice all lands you control.
        this.addAbility(new AsEntersBattlefieldAbility(new SacrificeAllLandEffect()));
        
        // Lands you control have "{tap}: Add two mana of any one color to your mana pool."
        SimpleManaAbility manaAbility = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(2), new TapSourceCost());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(manaAbility, Duration.WhileOnBattlefield, new FilterLandPermanent(), false)));
    }

    public OverlaidTerrain(final OverlaidTerrain card) {
        super(card);
    }

    @Override
    public OverlaidTerrain copy() {
        return new OverlaidTerrain(this);
    }
}

class SacrificeAllLandEffect extends OneShotEffect {

    SacrificeAllLandEffect() {
    super(Outcome.Detriment);
        staticText = "sacrifice all lands you control";
    }
    
    SacrificeAllLandEffect(final SacrificeAllLandEffect effect) {
        super(effect);
    }

   @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for(Permanent permanent : game.getBattlefield().getActivePermanents(new FilterControlledLandPermanent(), source.getControllerId(), game)){
              permanent.sacrifice(source.getControllerId(), game);
            }
            return true;
        }
        return false;
    }

    @Override
    public SacrificeAllLandEffect copy() {
        return new SacrificeAllLandEffect(this);
    }
}