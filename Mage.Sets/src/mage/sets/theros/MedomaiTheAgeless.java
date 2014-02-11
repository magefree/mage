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
package mage.sets.theros;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantAttackAllSourceEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class MedomaiTheAgeless extends CardImpl<MedomaiTheAgeless> {

    public MedomaiTheAgeless(UUID ownerId) {
        super(ownerId, 196, "Medomai the Ageless", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{W}{U}");
        this.expansionSetCode = "THS";
        this.supertype.add("Legendary");
        this.subtype.add("Sphinx");

        this.color.setBlue(true);
        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Medomai the Ageless deals combat damage to a player, take an extra turn after this one.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new AddExtraTurnControllerEffect(), false));
        // Medomai the Ageless can't attack during extra turns.
        Effect effect = new ConditionalRestrictionEffect(new CantAttackAllSourceEffect(Duration.WhileOnBattlefield), ExtraTurnCondition.getInstance());
        effect.setText("{this} can't attack during extra turns");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    public MedomaiTheAgeless(final MedomaiTheAgeless card) {
        super(card);
    }

    @Override
    public MedomaiTheAgeless copy() {
        return new MedomaiTheAgeless(this);
    }
}

class ExtraTurnCondition implements Condition {

    private static ExtraTurnCondition fInstance = null;

    private ExtraTurnCondition() {}

    public static Condition getInstance() {
        if (fInstance == null) {
            fInstance = new ExtraTurnCondition();
        }
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getState().isExtraTurn();
    }
}
