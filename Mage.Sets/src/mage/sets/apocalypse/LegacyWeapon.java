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
package mage.sets.apocalypse;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromAnywhereTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author North
 */
public class LegacyWeapon extends CardImpl<LegacyWeapon> {

    public LegacyWeapon(UUID ownerId) {
        super(ownerId, 137, "Legacy Weapon", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{7}");
        this.expansionSetCode = "APC";
        this.supertype.add("Legendary");

        // {W}{U}{B}{R}{G}: Exile target permanent.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new ExileTargetEffect(),
                new ManaCostsImpl("{W}{U}{B}{R}{G}"));
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
        // If Legacy Weapon would be put into a graveyard from anywhere, reveal Legacy Weapon and shuffle it into its owner's library instead.
        this.addAbility(new PutIntoGraveFromAnywhereTriggeredAbility(new LegacyWeaponEffect()));
    }

    public LegacyWeapon(final LegacyWeapon card) {
        super(card);
    }

    @Override
    public LegacyWeapon copy() {
        return new LegacyWeapon(this);
    }
}

class LegacyWeaponEffect extends OneShotEffect<LegacyWeaponEffect> {

    public LegacyWeaponEffect() {
        super(Outcome.Benefit);
        this.staticText = "reveal {this} and shuffle it into its owner's library instead";
    }

    public LegacyWeaponEffect(final LegacyWeaponEffect effect) {
        super(effect);
    }

    @Override
    public LegacyWeaponEffect copy() {
        return new LegacyWeaponEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            Player player = game.getPlayer(card.getOwnerId());
            if (player != null) {
                Cards cards = new CardsImpl();
                cards.add(card);
                player.revealCards("Legacy Weapon", cards, game);
                card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                player.shuffleLibrary(game);
                return true;
            }
        }
        return false;
    }
}
