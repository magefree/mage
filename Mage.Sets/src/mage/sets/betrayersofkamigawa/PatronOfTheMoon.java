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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.OfferingAbility;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

/**
 * @author LevelX2
 */
public class PatronOfTheMoon extends CardImpl {

    public PatronOfTheMoon(UUID ownerId) {
        super(ownerId, 45, "Patron of the Moon", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.expansionSetCode = "BOK";
        this.supertype.add("Legendary");
        this.subtype.add("Spirit");

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Moonfolk offering (You may cast this card any time you could cast an instant by sacrificing a Moonfolk and paying the difference in mana costs between this and the sacrificed Moonfolk. Mana cost includes color.)
        this.addAbility(new OfferingAbility("Moonfolk"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}: Put up to two land cards from your hand onto the battlefield tapped.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PatronOfTheMoonEffect(), new ManaCostsImpl("{1}"));
        this.addAbility(ability);

    }

    public PatronOfTheMoon(final PatronOfTheMoon card) {
        super(card);
    }

    @Override
    public PatronOfTheMoon copy() {
        return new PatronOfTheMoon(this);
    }
}

class PatronOfTheMoonEffect extends OneShotEffect {

    PatronOfTheMoonEffect() {
        super(Outcome.PutLandInPlay);
        staticText = "Put up to two land cards from your hand onto the battlefield tapped";
    }

    PatronOfTheMoonEffect(final PatronOfTheMoonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetCard target = new TargetCardInHand(0, 2, new FilterLandCard("up to two land cards to put onto the battlefield tapped"));
            controller.chooseTarget(outcome, controller.getHand(), target, source, game);
            return controller.moveCards(new CardsImpl(target.getTargets()).getCards(game),
                    Zone.BATTLEFIELD, source, game, true, false, false, null);
        }
        return false;
    }

    @Override
    public PatronOfTheMoonEffect copy() {
        return new PatronOfTheMoonEffect(this);
    }

}
