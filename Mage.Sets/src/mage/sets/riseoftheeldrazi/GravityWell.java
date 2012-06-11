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
package mage.sets.riseoftheeldrazi;

import java.util.Iterator;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class GravityWell extends CardImpl<GravityWell> {

    public GravityWell(UUID ownerId) {
        super(ownerId, 185, "Gravity Well", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{G}");
        this.expansionSetCode = "ROE";

        this.color.setGreen(true);

        // Whenever a creature with flying attacks, it loses flying until end of turn.
        this.addAbility(new GravityWellTriggeredAbility());
    }

    public GravityWell(final GravityWell card) {
        super(card);
    }

    @Override
    public GravityWell copy() {
        return new GravityWell(this);
    }
}

class GravityWellTriggeredAbility extends TriggeredAbilityImpl<GravityWellTriggeredAbility> {
    
    public GravityWellTriggeredAbility() {
	super(Constants.Zone.BATTLEFIELD, new GravityWellEffect());
    }

    public GravityWellTriggeredAbility(final GravityWellTriggeredAbility ability) {
	super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
	if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED ) {
            Permanent attacker = game.getPermanent(event.getSourceId());
            if (attacker != null && attacker.getAbilities().contains(FlyingAbility.getInstance())) {
                for (Effect effect : getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getSourceId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
	return "Whenever a creature with flying attacks, " + super.getRule();
    }

    @Override
    public GravityWellTriggeredAbility copy() {
	return new GravityWellTriggeredAbility(this);
    }
}

class GravityWellEffect extends ContinuousEffectImpl<GravityWellEffect> {
    
    public GravityWellEffect() {
	super(Constants.Duration.EndOfTurn, Constants.Outcome.LoseAbility);
	staticText = "it loses flying until end of turn";
    }

    public GravityWellEffect(final GravityWellEffect effect) {
	super(effect);
    }

    @Override
    public GravityWellEffect copy() {
	return new GravityWellEffect(this);
    }

    @Override
    public boolean apply(Constants.Layer layer, Constants.SubLayer sublayer, Ability source, Game game) {
	Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
	if (permanent != null) {
            switch (layer) {
                case AbilityAddingRemovingEffects_6:
                    if (sublayer == Constants.SubLayer.NA) {
			for (Iterator<Ability> i = permanent.getAbilities().iterator(); i.hasNext();) {
                            Ability entry = i.next();
                            if (entry.getId().equals(FlyingAbility.getInstance().getId()))
				i.remove();
			}
                    }
                    break;
            }
            return true;
        }
	return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
	return false;
    }

    @Override
    public boolean hasLayer(Constants.Layer layer) {
	return layer == Constants.Layer.AbilityAddingRemovingEffects_6;
    }

}