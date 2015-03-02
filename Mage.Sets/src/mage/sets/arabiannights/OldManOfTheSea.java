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
package mage.sets.arabiannights;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.EmptyEffect;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class OldManOfTheSea extends CardImpl {

    public OldManOfTheSea(UUID ownerId) {
        super(ownerId, 24, "Old Man of the Sea", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.expansionSetCode = "ARN";
        this.subtype.add("Djinn");

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // You may choose not to untap Old Man of the Sea during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());

        // {tap}: Gain control of target creature with power less than or equal to Old Man of the Sea's power for as long as Old Man of the Sea remains tapped and that creature's power remains less than or equal to Old Man of the Sea's power.
        FilterCreaturePermanent controllableCreatures = new FilterCreaturePermanent("creature with power less than or equal to Old Man of the Sea's power");
        controllableCreatures.add(new PowerLowerEqualSourcePredicate(this.getId()));
        ConditionalContinousEffect effect = new ConditionalContinousEffect(
                new OldManOfTheSeaGainControlTargetEffect(Duration.Custom, true), new CompoundCondition(SourceTappedCondition.getInstance(), new SourcePowerGreaterEqualTargetCondition()),
                "Gain control of target creature with power less than or equal to {this}'s power for as long as {this} remains tapped and that creature's power remains less than or equal to {this}'s power");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(controllableCreatures));
        this.addAbility(ability);
        // internal ability to check condition
        this.addAbility(new OldManOfTheSeaStateBasedTriggeredAbility());
    }

    public OldManOfTheSea(final OldManOfTheSea card) {
        super(card);
    }

    @Override
    public OldManOfTheSea copy() {
        return new OldManOfTheSea(this);
    }
}

class OldManOfTheSeaGainControlTargetEffect extends GainControlTargetEffect {

    public OldManOfTheSeaGainControlTargetEffect(Duration duration, boolean fixedControl) {
        super(duration, fixedControl);
    }

    public OldManOfTheSeaGainControlTargetEffect(final OldManOfTheSeaGainControlTargetEffect effect) {
        super(effect);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        // save target id to be available for hidden state based triggered effect
        game.getState().setValue("target" + source.getSourceId(), getTargetPointer().getFirst(game, source));
    }

    @Override
    public OldManOfTheSeaGainControlTargetEffect copy() {
        return new OldManOfTheSeaGainControlTargetEffect(this);
    }
}

/*
used a state based triggered effect here (not going to stack, so running hidden) to compare power of the controlled
creature to Old Man of the seas power. It's not possible to do this as condition of continuous effect, because the
time the effect checks its condition, the layered effects that modify power are not applied yet.
result is save to a state value to be available for the condition of the continuous effect
*/
class OldManOfTheSeaStateBasedTriggeredAbility extends StateTriggeredAbility {

    public OldManOfTheSeaStateBasedTriggeredAbility() {
        super(Zone.BATTLEFIELD, new EmptyEffect(""));
        this.setRuleVisible(false);
        this.usesStack = false;
    }

    public OldManOfTheSeaStateBasedTriggeredAbility(final OldManOfTheSeaStateBasedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OldManOfTheSeaStateBasedTriggeredAbility copy() {
        return new OldManOfTheSeaStateBasedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent sourcePermanent = game.getPermanent(getSourceId());
        if (sourcePermanent != null && sourcePermanent.isTapped()) {
            UUID controlledCreatureId = (UUID) game.getState().getValue("target" + getSourceId());
            if (controlledCreatureId != null) {
                Permanent controlledCreature = game.getPermanent(controlledCreatureId);
                if (controlledCreature != null) {
                    if (controlledCreature.getPower().getValue() > sourcePermanent.getPower().getValue()) {
                        game.getState().setValue("powerCondition" + getSourceId(), Boolean.TRUE);
                    }
                }
            }
        }
        return false;
    }

}

class SourcePowerGreaterEqualTargetCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Object object = game.getState().getValue("powerCondition" + source.getSourceId());
        if (object != null && (Boolean) object) {
            // reset the values
            game.getState().setValue("powerCondition" + source.getSourceId(), Boolean.FALSE);
            game.getState().setValue("target" + source.getSourceId(), null);
            // stop controlling target
            return false;
        }
        return true;
    }
}

class PowerLowerEqualSourcePredicate implements ObjectPlayerPredicate<ObjectPlayer<Permanent>> {

    UUID sourceId;

    public PowerLowerEqualSourcePredicate(UUID sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public boolean apply(ObjectPlayer<Permanent> input, Game game) {
        Permanent sourcePermanent = game.getPermanent(sourceId);
        Permanent permanent = input.getObject();
        if (permanent != null && sourcePermanent != null) {
            if (permanent.getPower().getValue() <= sourcePermanent.getPower().getValue()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "creature with power less than or equal to {source}'s power";
    }
}
