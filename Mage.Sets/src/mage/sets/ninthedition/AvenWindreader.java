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
package mage.sets.ninthedition;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author cbt33
 */
public class AvenWindreader extends CardImpl<AvenWindreader> {

    public AvenWindreader(UUID ownerId) {
        super(ownerId, 62, "Aven Windreader", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.expansionSetCode = "9ED";
        this.subtype.add("Bird");
        this.subtype.add("Soldier");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {1}{U}: Target player reveals the top card of his or her library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RevealTopCardTargetPlayerEffect(), new ManaCostsImpl("{1}{U}"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public AvenWindreader(final AvenWindreader card) {
        super(card);
    }

    @Override
    public AvenWindreader copy() {
        return new AvenWindreader(this);
    }
}

class RevealTopCardTargetPlayerEffect extends OneShotEffect<RevealTopCardTargetPlayerEffect> {
    
    public RevealTopCardTargetPlayerEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target player reveals the top card of his or her library.";
    }
    
    public RevealTopCardTargetPlayerEffect(final RevealTopCardTargetPlayerEffect effect) {
        super(effect);
    }
    
    @Override
    public RevealTopCardTargetPlayerEffect copy() {
        return new RevealTopCardTargetPlayerEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            CardsImpl cards = new CardsImpl();
            cards.add(player.getLibrary().removeFromTop(game));
            player.revealCards("Top card of target player's library", cards, game);
        }
        return false;
    }
}
