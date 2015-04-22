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
package mage.sets.tempestremastered;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.Token;

/**
 *
 * @author emerald000
 */
public class VolrathsLaboratory extends CardImpl {

    public VolrathsLaboratory(UUID ownerId) {
        super(ownerId, 234, "Volrath's Laboratory", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.expansionSetCode = "TPR";

        // As Volrath's Laboratory enters the battlefield, choose a color and a creature type.
        Ability ability = new EntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral), null, true, "As Volrath's Laboratory enters the battlefield, choose a color and a creature type.", "");
        ability.addEffect(new ChooseCreatureTypeEffect(Outcome.Neutral));
        this.addAbility(ability);
        
        // {5}, {T}: Put a 2/2 creature token of the chosen color and type onto the battlefield.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new VolrathsLaboratoryEffect(), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public VolrathsLaboratory(final VolrathsLaboratory card) {
        super(card);
    }

    @Override
    public VolrathsLaboratory copy() {
        return new VolrathsLaboratory(this);
    }
}

class VolrathsLaboratoryEffect extends OneShotEffect {
    
    VolrathsLaboratoryEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put a 2/2 creature token of the chosen color and type onto the battlefield";
    }
    
    VolrathsLaboratoryEffect(final VolrathsLaboratoryEffect effect) {
        super(effect);
    }
    
    @Override
    public VolrathsLaboratoryEffect copy() {
        return new VolrathsLaboratoryEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
        String type = (String) game.getState().getValue(source.getSourceId() + "_type");
        Token token = new VolrathsLaboratoryToken(color, type);
        return token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
    }
}

class VolrathsLaboratoryToken extends Token {

    VolrathsLaboratoryToken(ObjectColor color, String type) {
        super(type, "2/2 creature token of the chosen color and type");
        cardType.add(CardType.CREATURE);
        if (color != null) {
            this.color.setColor(color);
        }
        if (type != null) {
            subtype.add(type);
        }
        power = new MageInt(2);
        toughness = new MageInt(2);
    }
}
