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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author North
 */
public class StitchersApprentice extends CardImpl<StitchersApprentice> {

    public StitchersApprentice(UUID ownerId) {
        super(ownerId, 81, "Stitcher's Apprentice", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Homunculus");

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {1}{U}, {tap}: Put a 2/2 blue Homunculus creature token onto the battlefield, then sacrifice a creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new HomunculusToken()), new ManaCostsImpl("{1}{U}"));
        ability.addEffect(new StitchersApprenticeEffect());
        this.addAbility(ability);
    }

    public StitchersApprentice(final StitchersApprentice card) {
        super(card);
    }

    @Override
    public StitchersApprentice copy() {
        return new StitchersApprentice(this);
    }
}

class StitchersApprenticeEffect extends OneShotEffect<StitchersApprenticeEffect> {

    public StitchersApprenticeEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "then sacrifice a creature";
    }

    public StitchersApprenticeEffect(final StitchersApprenticeEffect effect) {
        super(effect);
    }

    @Override
    public StitchersApprenticeEffect copy() {
        return new StitchersApprenticeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Target target = new TargetControlledPermanent(new FilterControlledCreaturePermanent());

            if (target.canChoose(player.getId(), game) && player.choose(Outcome.Sacrifice, target, source.getSourceId(), game)) {
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    return permanent.sacrifice(source.getSourceId(), game);
                }
            }
        }
        return false;
    }
}

class HomunculusToken extends Token {

    public HomunculusToken() {
        super("Homunculus", "2/2 blue Homunculus creature");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add("Homunculus");
        power = new MageInt(2);
        toughness = new MageInt(2);
    }
}
