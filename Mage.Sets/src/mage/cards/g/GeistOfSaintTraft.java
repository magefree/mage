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
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.AngelToken;
import mage.players.Player;

/**
 * @author Loki
 */
public class GeistOfSaintTraft extends CardImpl {

    public GeistOfSaintTraft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");
        this.supertype.add("Legendary");
        this.subtype.add("Spirit");
        this.subtype.add("Cleric");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
        // Whenever Geist of Saint Traft attacks, create a 4/4 white Angel creature token with flying tapped and attacking. Exile that token at end of combat.
        this.addAbility(new AttacksTriggeredAbility(new GeistOfSaintTraftEffect(), false));
    }

    public GeistOfSaintTraft(final GeistOfSaintTraft card) {
        super(card);
    }

    @Override
    public GeistOfSaintTraft copy() {
        return new GeistOfSaintTraft(this);
    }
}

class GeistOfSaintTraftEffect extends OneShotEffect {

    GeistOfSaintTraftEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create a 4/4 white Angel creature token with flying tapped and attacking. Exile that token at end of combat";
    }

    GeistOfSaintTraftEffect(final GeistOfSaintTraftEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {        
        CreateTokenEffect effect = new CreateTokenEffect(new AngelToken());        
        Player controller = game.getPlayer(source.getControllerId());
        
        if (controller != null && effect.apply(game, source)) {
            effect.exileTokensCreatedAtNextEndStep(game, source);
            return true;
        }
        return false;
    }

    @Override
    public GeistOfSaintTraftEffect copy() {
        return new GeistOfSaintTraftEffect(this);
    }
}
