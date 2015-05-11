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
package mage.sets.fallenempires;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LookLibraryTopCardTargetPlayerEffect;
import mage.abilities.effects.common.RevealTargetPlayerLibraryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author anonymous
 */
public class OrcishSpy extends CardImpl {

    public OrcishSpy(UUID ownerId) {
        super(ownerId, 124, "Orcish Spy", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{R}");
        this.expansionSetCode = "FEM";
        this.subtype.add("Orc");
        this.subtype.add("Rogue");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Look at the top three cards of target player's library.
        Ability ability = new SimpleActivatedAbility(Zone.LIBRARY, new LookLibraryTopXCardsTargetPlayerEffect(3, "look at the top three cards of target player's library"), new TapSourceCost());
        this.addAbility(ability);
    }

    public OrcishSpy(final OrcishSpy card) {
        super(card);
    }

    @Override
    public OrcishSpy copy() {
        return new OrcishSpy(this);
    }
}

class LookLibraryTopXCardsTargetPlayerEffect extends OneShotEffect {

    protected int number;

    public LookLibraryTopXCardsTargetPlayerEffect(int number, String text) {
        super(Outcome.Benefit);
        this.number = number;
        this.staticText = text;
    }

    public LookLibraryTopXCardsTargetPlayerEffect(final LookLibraryTopXCardsTargetPlayerEffect effect) {
        super(effect);
    }

    @Override
    public LookLibraryTopXCardsTargetPlayerEffect copy() {
        return new LookLibraryTopXCardsTargetPlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (player != null && targetPlayer != null && sourceObject != null) {
            CardsImpl cards = new CardsImpl();
            cards.addAll(targetPlayer.getLibrary().getTopCards(game, number));            
            player.lookAtCards(sourceObject.getName(), cards, game);
            return true;
        }
        return false;
    }
}
