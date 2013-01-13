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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public class RapidHybridization extends CardImpl<RapidHybridization> {

    public RapidHybridization(UUID ownerId) {
        super(ownerId, 44, "Rapid Hybridization", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{U}");
        this.expansionSetCode = "GTC";

        this.color.setBlue(true);

        // Destroy target creature. It can't be regenerated. That creature's controller puts a 3/3 green Frog Lizard creature token onto the battlefield.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new RapidHybridizationEffect());
    }

    public RapidHybridization(final RapidHybridization card) {
        super(card);
    }

    @Override
    public RapidHybridization copy() {
        return new RapidHybridization(this);
    }
}

class RapidHybridizationEffect extends OneShotEffect<RapidHybridizationEffect> {

    public RapidHybridizationEffect() {
        super(Constants.Outcome.PutCreatureInPlay);
        staticText = "That creature's controller puts a 3/3 green Frog Lizard creature token onto the battlefield";
    }

    public RapidHybridizationEffect(final RapidHybridizationEffect effect) {
        super(effect);
    }

    @Override
    public RapidHybridizationEffect copy() {
        return new RapidHybridizationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) game.getLastKnownInformation(targetPointer.getFirst(game, source), Constants.Zone.BATTLEFIELD);
        if (permanent != null) {
            RapidHybridizationToken token = new RapidHybridizationToken();
            token.putOntoBattlefield(1, game, source.getId(), permanent.getControllerId());
        }
        return true;
    }

}

class RapidHybridizationToken extends Token {

    public RapidHybridizationToken() {
        super("Frog Lizard", "3/3 green Frog Lizard creature token onto the battlefield");
        cardType.add(CardType.CREATURE);
        
        color = ObjectColor.GREEN;
        
        subtype.add("Frog");
        subtype.add("Lizard");
        
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

}