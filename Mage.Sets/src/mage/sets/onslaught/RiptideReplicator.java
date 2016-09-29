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
package mage.sets.onslaught;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.token.Token;

/**
 *
 * @author HanClinto
 */
public class RiptideReplicator extends CardImpl {

    public RiptideReplicator(UUID ownerId) {
        super(ownerId, 309, "Riptide Replicator", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{X}{4}");
        this.expansionSetCode = "ONS";

        // As Riptide Replicator enters the battlefield, choose a color and a creature type.
        Ability ability = new EntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral));
        Effect effect = new ChooseColorEffect(Outcome.Neutral);
        effect.setText("and a creature type");
        ability.addEffect(new ChooseCreatureTypeEffect(Outcome.Neutral));
        this.addAbility(ability);
        
        // Riptide Replicator enters the battlefield with X charge counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.CHARGE.createInstance())));

        // {4}, {T}: Put an X/X creature token of the chosen color and type onto the battlefield, where X is the number of charge counters on Riptide Replicator.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RiptideReplicatorEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public RiptideReplicator(final RiptideReplicator card) {
        super(card);
    }

    @Override
    public RiptideReplicator copy() {
        return new RiptideReplicator(this);
    }
}

class RiptideReplicatorEffect extends OneShotEffect {

    RiptideReplicatorEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put an X/X creature token of the chosen color and type onto the battlefield, where X is the number of charge counters on Riptide Replicator.";
    }

    RiptideReplicatorEffect(final RiptideReplicatorEffect effect) {
        super(effect);
    }

    @Override
    public RiptideReplicatorEffect copy() {
        return new RiptideReplicatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
        String type = (String) game.getState().getValue(source.getSourceId() + "_type");
        int x = (new CountersSourceCount(CounterType.CHARGE)).calculate(game, source, this);
        Token token = new RiptideReplicatorToken(color, type, x);
        return token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
    }
}

class RiptideReplicatorToken extends Token {

    RiptideReplicatorToken(ObjectColor color, String type, int x) {
        super(type, "X/X creature token of the chosen color and type");
        cardType.add(CardType.CREATURE);
        if (color != null) {
            this.color.setColor(color);
        }
        if (type != null) {
            subtype.add(type);
        }
        power = new MageInt(x);
        toughness = new MageInt(x);
    }
}
