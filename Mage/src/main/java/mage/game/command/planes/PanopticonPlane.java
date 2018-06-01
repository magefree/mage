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
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ..AS IS.. AND ANY EXPRESS OR IMPLIED
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
package mage.game.command.planes;

import java.util.ArrayList;
import java.util.List;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MainPhaseStackEmptyCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.RollPlanarDieEffect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.Plane;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.Target;
import mage.watchers.common.PlanarRollWatcher;

/**
 *
 * @author spjspj
 */
public class PanopticonPlane extends Plane {

    private static final String rule = "At the beginning of your draw step, draw an additional card";

    public PanopticonPlane() {
        this.setName("Plane - Panopticon");
        this.setExpansionSetCodeForImage("PCA");

        // At the beginning of your draw step, draw an additional card.
        Ability ability = new BeginningOfDrawTriggeredAbility(new DrawCardTargetEffect(1), TargetController.ANY, false);
        this.getAbilities().add(ability);
        Ability pwability = new PanopticonTriggeredAbility(new DrawCardTargetEffect(1));
        this.getAbilities().add(pwability);

        // Active player can roll the planar die: Whenever you roll {CHAOS}, draw a card
        Effect chaosEffect = new DrawCardSourceControllerEffect(1);
        Target chaosTarget = null;

        List<Effect> chaosEffects = new ArrayList<>();
        chaosEffects.add(chaosEffect);

        List<Target> chaosTargets = new ArrayList<>();
        chaosTargets.add(chaosTarget);

        ActivateIfConditionActivatedAbility chaosAbility = new ActivateIfConditionActivatedAbility(Zone.COMMAND, new RollPlanarDieEffect(chaosEffects, chaosTargets), new GenericManaCost(0), MainPhaseStackEmptyCondition.instance);
        chaosAbility.addWatcher(new PlanarRollWatcher());
        this.getAbilities().add(chaosAbility);
        chaosAbility.setMayActivate(TargetController.ANY);
        this.getAbilities().add(new SimpleStaticAbility(Zone.ALL, new PlanarDieRollCostIncreasingEffect(chaosAbility.getOriginalId())));
    }
}

class PanopticonTriggeredAbility extends TriggeredAbilityImpl {

    public PanopticonTriggeredAbility(Effect effect) {
        super(Zone.COMMAND, effect);
    }

    public PanopticonTriggeredAbility(final PanopticonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.PLANESWALKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Plane cPlane = game.getState().getCurrentPlane();
        if (cPlane == null || !cPlane.getName().equalsIgnoreCase("Plane - Panopticon")) {
            return false;
        }

        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        if (activePlayer != null) {
            activePlayer.drawCards(1, game);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When you planeswalk to {this}, draw a card";
    }

    @Override
    public PanopticonTriggeredAbility copy() {
        return new PanopticonTriggeredAbility(this);
    }
}
