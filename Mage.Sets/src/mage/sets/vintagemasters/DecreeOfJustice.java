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
package mage.sets.vintagemasters;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.token.AngelToken;
import mage.game.permanent.token.SoldierToken;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class DecreeOfJustice extends CardImpl {

    public DecreeOfJustice(UUID ownerId) {
        super(ownerId, 22, "Decree of Justice", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{X}{X}{2}{W}{W}");
        this.expansionSetCode = "VMA";


        // Put X 4/4 white Angel creature tokens with flying onto the battlefield.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new AngelToken("C14"), new ManacostVariableValue()));
        
        // Cycling {2}{W}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}{W}")));
        
        // When you cycle Decree of Justice, you may pay {X}. If you do, put X 1/1 white Soldier creature tokens onto the battlefield.
        Ability ability = new CycleTriggeredAbility(new DecreeOfJusticeCycleEffect(), true);
        this.addAbility(ability);
    }

    public DecreeOfJustice(final DecreeOfJustice card) {
        super(card);
    }

    @Override
    public DecreeOfJustice copy() {
        return new DecreeOfJustice(this);
    }
}

class DecreeOfJusticeCycleEffect extends OneShotEffect {
    
    DecreeOfJusticeCycleEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may pay {X}. If you do, put X 1/1 white Soldier creature tokens onto the battlefield";
    }
    
    DecreeOfJusticeCycleEffect(final DecreeOfJusticeCycleEffect effect) {
        super(effect);
    }
    
    @Override
    public DecreeOfJusticeCycleEffect copy() {
        return new DecreeOfJusticeCycleEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ManaCosts<ManaCost> cost = new ManaCostsImpl<>("{X}");
        if (player != null) {
            int costX = player.announceXMana(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source);
            cost.add(new GenericManaCost(costX));
            if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false)) {
                Token token = new SoldierToken();
                token.putOntoBattlefield(costX, game, source.getSourceId(), source.getControllerId());
            }
        }
        return false;
    }
}
