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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class DeathmistRaptor extends CardImpl {

    public DeathmistRaptor(UUID ownerId) {
        super(ownerId, 180, "Deathmist Raptor", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Lizard");
        this.subtype.add("Beast");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever a permanent you control is turned face up, you may return Deathmist Raptor from your graveyard to the battlefield face up or face down.
        this.addAbility(new TurnedFaceUpAllTriggeredAbility(Zone.GRAVEYARD, new DeathmistRaptorEffect(), new FilterControlledPermanent("a permanent you control"), false, true));

        // Megamorph {4}{G}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{4}{G}"), true));
    }

    public DeathmistRaptor(final DeathmistRaptor card) {
        super(card);
    }

    @Override
    public DeathmistRaptor copy() {
        return new DeathmistRaptor(this);
    }
}

class DeathmistRaptorEffect extends OneShotEffect {

    public DeathmistRaptorEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may return {this} from your graveyard to the battlefield face up or face down";
    }

    public DeathmistRaptorEffect(final DeathmistRaptorEffect effect) {
        super(effect);
    }

    @Override
    public DeathmistRaptorEffect copy() {
        return new DeathmistRaptorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        if (controller != null && (sourceObject instanceof Card)) {
            return controller.moveCards((Card) sourceObject, Zone.BATTLEFIELD, source, game, false,
                    controller.chooseUse(Outcome.Detriment, "Return " + sourceObject.getLogName() + " face down to battlefield (otherwise face up)?", source, game),
                    false, null);
        }
        return false;
    }
}
