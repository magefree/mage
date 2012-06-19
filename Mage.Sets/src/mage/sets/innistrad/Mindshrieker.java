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
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward
 */
public class Mindshrieker extends CardImpl<Mindshrieker> {

    public Mindshrieker(UUID ownerId) {
        super(ownerId, 67, "Mindshrieker", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Spirit");
        this.subtype.add("Bird");

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        // {2}: Target player puts the top card of his or her library into his or her graveyard. Mindshrieker gets +X/+X until end of turn, where X is that card's converted mana cost.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MindshriekerEffect(), new ManaCostsImpl("{2}"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

    }

    public Mindshrieker(final Mindshrieker card) {
        super(card);
    }

    @Override
    public Mindshrieker copy() {
        return new Mindshrieker(this);
    }
}

class MindshriekerEffect extends OneShotEffect<MindshriekerEffect> {

    public MindshriekerEffect() {
        super(Outcome.BoostCreature);
        staticText = "Target player puts the top card of his or her library into his or her graveyard. {this} gets +X/+X until end of turn, where X is that card's converted mana cost";
    }

    public MindshriekerEffect(final MindshriekerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null && player.getLibrary().size() > 0) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                card.moveToZone(Zone.GRAVEYARD, source.getId(), game, false);
                int amount = card.getManaCost().convertedManaCost();
                if (amount > 0)
                    game.addEffect(new BoostSourceEffect(amount, amount, Duration.EndOfTurn), source);
                return true;
            }
        }
        return false;
    }

    @Override
    public MindshriekerEffect copy() {
        return new MindshriekerEffect(this);
    }

}