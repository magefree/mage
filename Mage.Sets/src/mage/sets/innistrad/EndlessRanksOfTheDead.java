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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author BetaSteward
 */
public class EndlessRanksOfTheDead extends CardImpl<EndlessRanksOfTheDead> {

    public EndlessRanksOfTheDead(UUID ownerId) {
        super(ownerId, 99, "Endless Ranks of the Dead", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");
        this.expansionSetCode = "ISD";

        this.color.setBlack(true);

        // At the beginning of your upkeep, put X 2/2 black Zombie creature tokens onto the battlefield, where X is half the number of Zombies you control, rounded down.
        this.addAbility(new OnEventTriggeredAbility(EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new CreateTokenEffect(new ZombieToken(), HalfZombiesCount.getInstance())));

    }

    public EndlessRanksOfTheDead(final EndlessRanksOfTheDead card) {
        super(card);
    }

    @Override
    public EndlessRanksOfTheDead copy() {
        return new EndlessRanksOfTheDead(this);
    }
}

class HalfZombiesCount implements DynamicValue {

    private static HalfZombiesCount instance;
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.getSubtype().add("Zombie");
    }

    public static HalfZombiesCount getInstance() {
        if (instance == null) {
            instance = new HalfZombiesCount();
        }
        return instance;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        int amount = game.getBattlefield().countAll(filter, sourceAbility.getControllerId(), game) / 2;
        return amount;
    }

    @Override
    public DynamicValue clone() {
        return getInstance();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "half the number of Zombies you control, rounded down";
    }
}