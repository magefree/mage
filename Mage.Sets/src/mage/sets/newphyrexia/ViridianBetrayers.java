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
package mage.sets.newphyrexia;

import java.util.Set;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class ViridianBetrayers extends CardImpl<ViridianBetrayers> {

    private static final String rule = "{this} has infect as long as an opponent is poisoned.";

    public ViridianBetrayers(UUID ownerId) {
        super(ownerId, 124, "Viridian Betrayers", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.expansionSetCode = "NPH";
        this.subtype.add("Elf");
        this.subtype.add("Warrior");

        this.color.setGreen(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Viridian Betrayers has infect as long as an opponent is poisoned.
        ConditionalContinousEffect effect = new ConditionalContinousEffect(new GainAbilitySourceEffect(InfectAbility.getInstance()), PoisonedCondition.getInstance(), rule);
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, effect));
    }

    public ViridianBetrayers(final ViridianBetrayers card) {
        super(card);
    }

    @Override
    public ViridianBetrayers copy() {
        return new ViridianBetrayers(this);
    }
}

class PoisonedCondition implements Condition {

    private static PoisonedCondition fInstance = new PoisonedCondition();

    public static Condition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        for (UUID opponentUuid : opponents) {
            Player opponent = game.getPlayer(opponentUuid);
            if (opponent != null && opponent.getCounters().getCount(CounterType.POISON) > 0) {
                return true;
            }
        }
        return false;
    }
}
