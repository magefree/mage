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

package mage.sets.worldwake;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author Loki
 */
public class WalkingAtlas extends CardImpl<WalkingAtlas> {

    public WalkingAtlas (UUID ownerId) {
        super(ownerId, 131, "Walking Atlas", Rarity.COMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Construct");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new WalkingAtlasEffect(), new TapSourceCost());
        ability.addTarget(new TargetCardInHand(new FilterLandCard()));
        this.addAbility(ability);
    }

    public WalkingAtlas (final WalkingAtlas card) {
        super(card);
    }

    @Override
    public WalkingAtlas copy() {
        return new WalkingAtlas(this);
    }

}

class WalkingAtlasEffect extends OneShotEffect<WalkingAtlasEffect> {
    WalkingAtlasEffect() {
        super(Constants.Outcome.PutLandInPlay);
        staticText = "You may put a land card from your hand onto the battlefield";
    }

    WalkingAtlasEffect(final WalkingAtlasEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card c = game.getCard(targetPointer.getFirst(source));
        if (c != null) {
            c.moveToZone(Constants.Zone.BATTLEFIELD, source.getSourceId(), game, false);
            return true;
        }
        return false;
    }

    @Override
    public WalkingAtlasEffect copy() {
        return new WalkingAtlasEffect(this);
    }

}