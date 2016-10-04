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
package mage.sets.conspiracy;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class GrenzoDungeonWarden extends CardImpl {

    public GrenzoDungeonWarden(UUID ownerId) {
        super(ownerId, 47, "Grenzo, Dungeon Warden", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{X}{B}{R}");
        this.expansionSetCode = "CNS";
        this.supertype.add("Legendary");
        this.subtype.add("Goblin");
        this.subtype.add("Rogue");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Grenzo, Dungeon Warden enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));

        // {2}: Put the bottom card of your library into your graveyard. If it's a creature card with power less than or equal to Grenzo's power, put it onto the battlefield.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GrenzoDungeonWardenEffect(), new GenericManaCost(2)));
    }

    public GrenzoDungeonWarden(final GrenzoDungeonWarden card) {
        super(card);
    }

    @Override
    public GrenzoDungeonWarden copy() {
        return new GrenzoDungeonWarden(this);
    }
}

class GrenzoDungeonWardenEffect extends OneShotEffect {

    GrenzoDungeonWardenEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put the bottom card of your library into your graveyard. If it's a creature card with power less than or equal to {this}'s power, put it onto the battlefield";
    }

    GrenzoDungeonWardenEffect(final GrenzoDungeonWardenEffect effect) {
        super(effect);
    }

    @Override
    public GrenzoDungeonWardenEffect copy() {
        return new GrenzoDungeonWardenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.getLibrary().size() > 0) {
                Card card = controller.getLibrary().getFromBottom(game);
                if (card != null) {
                    controller.moveCards(card, Zone.GRAVEYARD, source, game);
                    if (card.getCardType().contains(CardType.CREATURE)) {
                        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
                        if (sourcePermanent != null && card.getPower().getValue() <= sourcePermanent.getPower().getValue()) {
                            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
