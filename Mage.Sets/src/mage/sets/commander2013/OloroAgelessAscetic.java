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
package mage.sets.commander2013;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUntapTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class OloroAgelessAscetic extends CardImpl<OloroAgelessAscetic> {

    public OloroAgelessAscetic(UUID ownerId) {
        super(ownerId, 203, "Oloro, Ageless Ascetic", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{W}{U}{B}");
        this.expansionSetCode = "C13";
        this.supertype.add("Legendary");
        this.subtype.add("Giant");
        this.subtype.add("Soldier");

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // At the beginning of your upkeep, you gain 2 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GainLifeEffect(2), TargetController.YOU, false));
        // Whenever you gain life, you may pay {1}. If you do, draw a card and each opponent loses 1 life.
        this.addAbility(new GainLifeControllerTriggeredAbility(new DoIfCostPaid(new OloroAgelessAsceticEffect(), new GenericManaCost(1)),false));
        // At the beginning of your upkeep, if Oloro, Ageless Ascetic is in the command zone, you gain 2 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.COMMAND,
                new GainLifeEffect(new StaticValue(2), "if Oloro, Ageless Ascetic is in the command zone, you gain 2 life"),TargetController.YOU, false));
    }

    public OloroAgelessAscetic(final OloroAgelessAscetic card) {
        super(card);
    }

    @Override
    public OloroAgelessAscetic copy() {
        return new OloroAgelessAscetic(this);
    }
}

class OloroAgelessAsceticEffect extends OneShotEffect<OloroAgelessAsceticEffect> {

    public OloroAgelessAsceticEffect() {
        super(Outcome.Benefit);
        this.staticText = "draw a card and each opponent loses 1 life";
    }

    public OloroAgelessAsceticEffect(final OloroAgelessAsceticEffect effect) {
        super(effect);
    }

    @Override
    public OloroAgelessAsceticEffect copy() {
        return new OloroAgelessAsceticEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        new DrawCardControllerEffect(1).apply(game, source);
        new DamagePlayersEffect(1, TargetController.OPPONENT).apply(game, source);
        return false;
    }
}
