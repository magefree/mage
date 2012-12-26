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
package mage.sets.conflux;

import java.util.ArrayList;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DevourEffect;
import mage.abilities.effects.common.DevourEffect.DevourFactor;
import mage.abilities.keyword.DevourAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author LevelX2
 */
public class VoraciousDragon extends CardImpl<VoraciousDragon> {

    public VoraciousDragon(UUID ownerId) {
        super(ownerId, 75, "Voracious Dragon", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.expansionSetCode = "CON";
        this.subtype.add("Dragon");

        this.color.setRed(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Devour 1 (As this enters the battlefield, you may sacrifice any number of creatures. This creature enters the battlefield with that many +1/+1 counters on it.)
        this.addAbility(new DevourAbility(DevourFactor.Devour1));

        // When Voracious Dragon enters the battlefield, it deals damage to target creature or player equal to twice the number of Goblins it devoured.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(new TwiceDevouredGoblins()), false);
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
    }

    public VoraciousDragon(final VoraciousDragon card) {
        super(card);
    }

    @Override
    public VoraciousDragon copy() {
        return new VoraciousDragon(this);
    }
}

class TwiceDevouredGoblins implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        Permanent sourcePermanent = game.getPermanent(sourceAbility.getSourceId());
        if (sourcePermanent != null) {
            for (Ability ability : sourcePermanent.getAbilities()) {
                if (ability instanceof DevourAbility) {
                    for (Effect effect: ability.getEffects()) {
                        if (effect instanceof DevourEffect) {
                            DevourEffect devourEffect = (DevourEffect) effect;
                            int amountGoblins = 0;
                            for (ArrayList<String> subtypesItem :devourEffect.getSubtypes(game, sourcePermanent.getId())) {
                                if (subtypesItem.contains("Goblin")) {
                                    ++amountGoblins;
                                }
                            }
                            return amountGoblins *2;
                        }
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public TwiceDevouredGoblins copy() {
        return new TwiceDevouredGoblins();
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public String getMessage() {
        return "twice the number of Goblins it devoured";
    }
}
