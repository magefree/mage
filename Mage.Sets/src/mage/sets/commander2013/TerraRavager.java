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
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class TerraRavager extends CardImpl<TerraRavager> {

    public TerraRavager(UUID ownerId) {
        super(ownerId, 126, "Terra Ravager", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.expansionSetCode = "C13";
        this.subtype.add("Elemental");
        this.subtype.add("Beast");

        this.color.setRed(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Whenever Terra Ravager attacks, it gets +X/+0 until end of turn, where X is the number of lands defending player controls.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(new TerraRavagerLandCount(), new StaticValue(0), Duration.EndOfTurn, true), false));
    }

    public TerraRavager(final TerraRavager card) {
        super(card);
    }

    @Override
    public TerraRavager copy() {
        return new TerraRavager(this);
    }
}

class TerraRavagerLandCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        for (CombatGroup group :game.getCombat().getGroups()) {
            if (group.getAttackers().contains(sourceAbility.getSourceId())) {
                UUID defenderId = group.getDefenderId();
                if (group.isDefenderIsPlaneswalker()) {
                    Permanent permanent = game.getPermanent(defenderId);
                    if (permanent != null) {
                        defenderId = permanent.getControllerId();
                    }
                }
                return game.getBattlefield().countAll(new FilterLandPermanent(), defenderId, game);

            }
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return new TerraRavagerLandCount();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "land defending player controls";
    }
}
