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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MultikickerAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class VoyagerDrake extends CardImpl<VoyagerDrake> {

    public VoyagerDrake(UUID ownerId) {
        super(ownerId, 45, "Voyager Drake", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Drake");

        this.color.setBlue(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Multikicker {U}
        this.addAbility(new MultikickerAbility("{U}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Voyager Drake enters the battlefield, up to X target creatures gain flying until end of turn, where X is the number of times Voyager Drake was kicked.
        Ability ability = new ConditionalTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Constants.Duration.EndOfTurn), false),
                KickedCondition.getInstance(),
                "When {this} enters the battlefield, up to X target creatures gain flying until end of turn, where X is the number of times {this} was kicked.");
        this.addAbility(ability);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof ConditionalTriggeredAbility) {
            ability.getTargets().clear();
            int numbTargets = new MultikickerCount().calculate(game, ability);
            if (numbTargets > 0) {
                ability.addTarget(new TargetCreaturePermanent(0,numbTargets));
            }
        }
    }
    public VoyagerDrake(final VoyagerDrake card) {
        super(card);
    }

    @Override
    public VoyagerDrake copy() {
        return new VoyagerDrake(this);
    }
}
