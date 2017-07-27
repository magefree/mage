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
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ModeChoiceSourceCondition;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.PermanentsEnterBattlefieldTappedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Eirkei
 */
public class AshlingsPrerogative extends CardImpl {

    public AshlingsPrerogative(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");

        // As Ashling's Prerogative enters the battlefield, choose odd or even.
        this.addAbility(new EntersBattlefieldAbility(new ChooseModeEffect("Odd or even?", "Odd", "Even"), null, "As {this} enters the battlefield, choose odd or even. (Zero is even.)", ""));

        // Each creature with converted mana cost of the chosen value has haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AshlingsPrerogativeCorrectOddityEffect()));

        // Each creature without converted mana cost of the chosen value enters the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AshlingsPrerogativeIncorrectOddityEffect()));

    }

    public AshlingsPrerogative(final AshlingsPrerogative card) {
        super(card);
    }

    @Override
    public AshlingsPrerogative copy() {
        return new AshlingsPrerogative(this);
    }
}

class AshlingsPrerogativeIncorrectOddityEffect extends PermanentsEnterBattlefieldTappedEffect {

    private static final FilterCreaturePermanent creaturefilter = new FilterCreaturePermanent("Each creature without converted mana cost of the chosen value");
    private static final ModeChoiceSourceCondition oddCondition = new ModeChoiceSourceCondition("Odd");

    public AshlingsPrerogativeIncorrectOddityEffect() {
        super(creaturefilter);
        staticText = "Each creature without converted mana cost of the chosen value enters the battlefield tapped.";
    }
    
    public AshlingsPrerogativeIncorrectOddityEffect(final AshlingsPrerogativeIncorrectOddityEffect effect) {
        super(effect);   
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        int incorrectModResult;

        if (oddCondition.apply(game, source)) {
            incorrectModResult = 0;
        } else {
            incorrectModResult = 1;
        }

        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();

        return permanent != null && creaturefilter.match(permanent, game) && permanent.getConvertedManaCost() % 2 == incorrectModResult;
    }
    
    @Override
    public AshlingsPrerogativeIncorrectOddityEffect copy() {
        return new AshlingsPrerogativeIncorrectOddityEffect(this);
    }
}

class AshlingsPrerogativeCorrectOddityEffect extends GainAbilityAllEffect {

    private static final FilterCreaturePermanent creaturefilter = new FilterCreaturePermanent("Each creature with converted mana cost of the chosen value");
    private static final ModeChoiceSourceCondition oddCondition = new ModeChoiceSourceCondition("Odd");

    public AshlingsPrerogativeCorrectOddityEffect() {
        super(HasteAbility.getInstance(), Duration.WhileOnBattlefield, creaturefilter);
        staticText = "Each creature with converted mana cost of the chosen value has haste.";
    }
    public AshlingsPrerogativeCorrectOddityEffect(final AshlingsPrerogativeCorrectOddityEffect effect) {
        super(effect);   
    }

    @Override
    protected boolean selectedByRuntimeData(Permanent permanent, Ability source, Game game) {
        int correctModResult;
        if (oddCondition.apply(game, source)) {
            correctModResult = 1;
        } else {
            correctModResult = 0;
        }
        return permanent != null && creaturefilter.match(permanent, game) && permanent.getConvertedManaCost() % 2 == correctModResult;
    }
    
    @Override
    public AshlingsPrerogativeCorrectOddityEffect copy() {
        return new AshlingsPrerogativeCorrectOddityEffect(this);
    }
}
