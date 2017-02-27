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
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.ParleyCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class WoodvineElemental extends CardImpl {
    
    static final private String rule = "<i>Parley &mdash; </i> Whenever {this} attacks, each player reveals the top card of his or her library. "
                        + "For each nonland card revealed this way, attacking creatures you control get +1/+1 until end of turn. Then each player draws a card.";
    
    public WoodvineElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{W}");
        this.subtype.add("Elemental");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // Parley - Whenever Woodvine Elemental attacks, each player reveals the top card of his or her library. 
        // For each nonland card revealed this way, attacking creatures you control get +1/+1 until end of turn. Then each player draws a card.
        Ability ability = new AttacksTriggeredAbility(new WoodvineElementalEffect(), false, rule);
        Effect effect = new DrawCardAllEffect(1);
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public WoodvineElemental(final WoodvineElemental card) {
        super(card);
    }

    @Override
    public WoodvineElemental copy() {
        return new WoodvineElemental(this);
    }
}

class WoodvineElementalEffect extends OneShotEffect {

    public WoodvineElementalEffect() {
        super(Outcome.Benefit);
    }

    public WoodvineElementalEffect(final WoodvineElementalEffect effect) {
        super(effect);
    }

    @Override
    public WoodvineElementalEffect copy() {
        return new WoodvineElementalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int parley = ParleyCount.getInstance().calculate(game, source, this);
            if (parley > 0) {
                game.addEffect(new BoostControlledEffect(parley, parley, Duration.EndOfTurn, new FilterAttackingCreature("Attacking creatures"), false), source);
            }
            return true;
        }
        return false;
    }
}