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
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPostCombatMainTriggeredAbility;
import mage.abilities.dynamicvalue.common.OpponentsLostLifeCount;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.keyword.AfflictAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public class NehebTheEternal extends CardImpl {

    public NehebTheEternal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Zombie");
        this.subtype.add("Minotaur");
        this.subtype.add("Warrior");
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Afflict 3
        addAbility(new AfflictAbility(3));

        // At the beginning of your postcombat main phase, add {R} to your mana pool for each 1 life your opponents have lost this turn.
        this.addAbility(new BeginningOfPostCombatMainTriggeredAbility(new NehebTheEternalManaEffect(), TargetController.YOU, false));
    }

    public NehebTheEternal(final NehebTheEternal card) {
        super(card);
    }

    @Override
    public NehebTheEternal copy() {
        return new NehebTheEternal(this);
    }
}

class NehebTheEternalManaEffect extends ManaEffect {

    NehebTheEternalManaEffect() {
        super();
        this.staticText = "add {R} to your mana pool for each 1 life your opponents have lost this turn";
    }

    NehebTheEternalManaEffect(final NehebTheEternalManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            OpponentsLostLifeCount dynamicValue = new OpponentsLostLifeCount();
            int amount = dynamicValue.calculate(game, source, this);
            if (amount > 0) {
                controller.getManaPool().addMana(Mana.RedMana(amount), game, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public NehebTheEternalManaEffect copy() {
        return new NehebTheEternalManaEffect(this);
    }

    @Override
    public Mana getMana(Game game, Ability source) {
        return null;
    }
}
