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
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FlippedCondition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.CopyTokenEffect;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public class StudentOfElements extends CardImpl<StudentOfElements> {

    public StudentOfElements(UUID ownerId) {
        super(ownerId, 93, "Student of Elements", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.flipCard = true;
        this.flipCardName = "Tobita, Master of Winds";

        // When Student of Elements has flying, flip it.
        this.addAbility(new StudentOfElementsHasFlyingAbility());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinousEffect(new CopyTokenEffect(new TobitaMasterOfWinds()), FlippedCondition.getInstance(), "")));

    }

    public StudentOfElements(final StudentOfElements card) {
        super(card);
    }

    @Override
    public StudentOfElements copy() {
        return new StudentOfElements(this);
    }
}

class StudentOfElementsHasFlyingAbility extends StateTriggeredAbility<StudentOfElementsHasFlyingAbility> {

    public StudentOfElementsHasFlyingAbility() {
        super(Zone.BATTLEFIELD, new FlipSourceEffect());
    }

    public StudentOfElementsHasFlyingAbility(final StudentOfElementsHasFlyingAbility ability) {
        super(ability);
    }

    @Override
    public StudentOfElementsHasFlyingAbility copy() {
        return new StudentOfElementsHasFlyingAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        if(permanent.getAbilities().contains(FlyingAbility.getInstance())){
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When {this} has flying, flip it.";
    }

}

class TobitaMasterOfWinds extends Token {

    TobitaMasterOfWinds() {
        super("Tobita, Master of Winds", "");
        supertype.add("Legendary");
        cardType.add(Constants.CardType.CREATURE);
        color.setBlue(true);
        subtype.add("Human");
        subtype.add("Wizard");
        power = new MageInt(3);
        toughness = new MageInt(3);

        // Creatures you control have flying.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD, new GainAbilityControlledEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield, new FilterCreaturePermanent())));
    }
}
