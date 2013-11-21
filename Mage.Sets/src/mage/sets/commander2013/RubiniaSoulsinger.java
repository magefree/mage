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
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.continious.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class RubiniaSoulsinger extends CardImpl<RubiniaSoulsinger> {

    public RubiniaSoulsinger(UUID ownerId) {
        super(ownerId, 207, "Rubinia Soulsinger", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}{W}{U}");
        this.expansionSetCode = "C13";
        this.supertype.add("Legendary");
        this.subtype.add("Faerie");

        this.color.setBlue(true);
        this.color.setGreen(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // You may choose not to untap Rubinia Soulsinger during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());
        // {tap}: Gain control of target creature for as long as you control Rubinia and Rubinia remains tapped.
        ConditionalContinousEffect effect = new ConditionalContinousEffect(
                new GainControlTargetEffect(Duration.OneUse),
                new RubiniaSoulsingerCondition(),
                "Gain control of target creature for as long as you control Rubinia and Rubinia remains tapped");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(true));
        this.addAbility(ability);
    }

    public RubiniaSoulsinger(final RubiniaSoulsinger card) {
        super(card);
    }

    @Override
    public RubiniaSoulsinger copy() {
        return new RubiniaSoulsinger(this);
    }
}

class RubiniaSoulsingerCondition implements Condition {

    private UUID controllerId;

    @Override
    public boolean apply(Game game, Ability source) {
        if (controllerId == null) {
            controllerId = source.getControllerId();
        }
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (permanent != null){
            if (permanent.isTapped()){
                return controllerId == source.getControllerId();
            }
        }
        return false;
    }
}
