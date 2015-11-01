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
package mage.sets.eventide;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public class DivinityOfPride extends CardImpl {

    public DivinityOfPride(UUID ownerId) {
        super(ownerId, 86, "Divinity of Pride", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{W/B}{W/B}{W/B}{W/B}{W/B}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Spirit");
        this.subtype.add("Avatar");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Divinity of Pride gets +4/+4 as long as you have 25 or more life.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new BoostSourceEffect(4, 4, Duration.WhileOnBattlefield),
                        new DivinityOfPrideCondition(),
                        "{this} gets +4/+4 as long as you have 25 or more life")));
    }

    public DivinityOfPride(final DivinityOfPride card) {
        super(card);
    }

    @Override
    public DivinityOfPride copy() {
        return new DivinityOfPride(this);
    }
}

class DivinityOfPrideCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.getLife() >= 25;
    }
}
