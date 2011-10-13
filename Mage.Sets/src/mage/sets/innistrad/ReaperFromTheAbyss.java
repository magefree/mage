/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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

package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ReaperFromTheAbyss extends CardImpl<ReaperFromTheAbyss> {

    private static FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Demon creature");
    
    static {
        filter.getSubtype().add("Demon");
        filter.setNotSubtype(true);
    }

    public ReaperFromTheAbyss(UUID ownerId) {
		super(ownerId, 112, "Reaper from the Abyss", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{B}");
		this.expansionSetCode = "ISD";
		this.subtype.add("Demon");
		this.color.setBlack(true);
		this.power = new MageInt(6);
		this.toughness = new MageInt(6);

		this.addAbility(FlyingAbility.getInstance());
        Ability ability = new ReaperFromTheAbyssAbility();
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
	}

	public ReaperFromTheAbyss(final ReaperFromTheAbyss card) {
		super(card);
	}

	@Override
	public ReaperFromTheAbyss copy() {
		return new ReaperFromTheAbyss(this);
	}

}

class ReaperFromTheAbyssAbility extends TriggeredAbilityImpl<ReaperFromTheAbyssAbility> {
    
    public ReaperFromTheAbyssAbility() {
        super(Constants.Zone.BATTLEFIELD, new DestroyTargetEffect(), false);
    }

    public ReaperFromTheAbyssAbility(final ReaperFromTheAbyssAbility ability) {
        super(ability);
    }

    @Override
    public ReaperFromTheAbyssAbility copy() {
        return new ReaperFromTheAbyssAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.END_TURN_STEP_PRE) {
            Watcher watcher = game.getState().getWatchers().get("Morbid");
            return watcher.conditionMet();
		}
		return false;
    }

    @Override
    public String getRule() {
        return "Morbid - At the beginning of each end step, if a creature died this turn, destroy target non-demon creature.";
    }
}
