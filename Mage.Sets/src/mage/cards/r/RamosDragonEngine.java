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
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.ActivateOncePerTurnManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public class RamosDragonEngine extends CardImpl {

    public RamosDragonEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Dragon");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever you cast a spell, put a +1/+1 counter on Ramos, Dragon Engine for each of that spell's colors.
        this.addAbility(new SpellCastControllerTriggeredAbility(new RamosDragonEngineAddCountersEffect(), new FilterSpell("a spell"), false, true));

        // Remove five +1/+1 counters from Ramos: Add {W}{W}{U}{U}{B}{B}{R}{R}{G}{G} to your mana pool. Activate this ability only once each turn.        
        Ability ability = new ActivateOncePerTurnManaAbility(Zone.BATTLEFIELD, new BasicManaEffect(new Mana(2, 2, 2, 2, 2, 0, 0, 0)), new RemoveCountersSourceCost(CounterType.P1P1.createInstance(5)));
        this.addAbility(ability);
    }

    public RamosDragonEngine(final RamosDragonEngine card) {
        super(card);
    }

    @Override
    public RamosDragonEngine copy() {
        return new RamosDragonEngine(this);
    }
}

class RamosDragonEngineAddCountersEffect extends OneShotEffect {

    public RamosDragonEngineAddCountersEffect() {
        super(Outcome.Benefit);
        staticText = "put a +1/+1 counter on {this} for each of that spell's colors";
    }

    public RamosDragonEngineAddCountersEffect(final RamosDragonEngineAddCountersEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (you != null && permanent != null) {
            Spell spell = game.getStack().getSpell(this.getTargetPointer().getFirst(game, source));
            if (spell != null) {
                int amount = 0;
                if (spell.getColor(game).isWhite()) {
                    ++amount;
                }
                if (spell.getColor(game).isBlue()) {
                    ++amount;
                }
                if (spell.getColor(game).isBlack()) {
                    ++amount;
                }
                if (spell.getColor(game).isRed()) {
                    ++amount;
                }
                if (spell.getColor(game).isGreen()) {
                    ++amount;
                }
                if (amount > 0) {
                    permanent.addCounters(CounterType.P1P1.createInstance(amount), source, game);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public RamosDragonEngineAddCountersEffect copy() {
        return new RamosDragonEngineAddCountersEffect(this);
    }
}
