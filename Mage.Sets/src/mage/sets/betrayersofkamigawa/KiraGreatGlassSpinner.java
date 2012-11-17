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
 *  CONTRIBUTORS BE LIAB8LE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
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
package mage.sets.betrayersofkamigawa;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.TargetStackObject;

/**
 *
 * @author LevelX2
 */
public class KiraGreatGlassSpinner extends CardImpl<KiraGreatGlassSpinner> {

    public KiraGreatGlassSpinner(UUID ownerId) {
        super(ownerId, 40, "Kira, Great Glass-Spinner", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Spirit");
        this.supertype.add("Legendary");
        this.color.setBlue(true);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Creatures you control have "Whenever this creature becomes the target of a spell or ability for the first time in a turn, counter that spell or ability."
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(new KiraGreatGlassSpinnerAbility(), Duration.WhileOnBattlefield, new FilterCreaturePermanent())));

    }

    public KiraGreatGlassSpinner(final KiraGreatGlassSpinner card) {
        super(card);
    }

    @Override
    public KiraGreatGlassSpinner copy() {
        return new KiraGreatGlassSpinner(this);
    }
}
// TODO:
// not perfectly implemented. It doesnt't handles if a creature was already targeted the turn Kira comes into play.
// Maybe it's better to implement it with a watcher

class KiraGreatGlassSpinnerAbility extends TriggeredAbilityImpl<KiraGreatGlassSpinnerAbility> {

    protected Map<UUID,Integer> turnUsed = new HashMap<UUID,Integer>();

    public KiraGreatGlassSpinnerAbility() {
        super(Zone.BATTLEFIELD, new CounterTargetEffect(), false);
    }

    public KiraGreatGlassSpinnerAbility(final KiraGreatGlassSpinnerAbility ability) {
        super(ability);
        turnUsed = ability.turnUsed;
    }

    @Override
    public KiraGreatGlassSpinnerAbility copy() {
        return new KiraGreatGlassSpinnerAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.TARGETED && event.getTargetId().equals(this.getSourceId())) {
            Integer turn = turnUsed.get(event.getTargetId());
            if (turn == null || turn.intValue() < game.getTurnNum()) {
                this.getTargets().clear();
                TargetStackObject target = new TargetStackObject();
                target.add(event.getSourceId(), game);
                this.addTarget(target);
                if (turnUsed.containsKey(event.getTargetId())) {
                    turnUsed.remove(event.getTargetId());
                    turnUsed.put(event.getTargetId(),new Integer(game.getTurnNum()));
                } else {
                    turnUsed.put(event.getTargetId(), new Integer(game.getTurnNum()));
                }

                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever this creature becomes the target of a spell or ability for the first time in a turn, counter that spell or ability.";
    }

}
