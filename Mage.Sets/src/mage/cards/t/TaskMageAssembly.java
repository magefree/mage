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
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public class TaskMageAssembly extends CardImpl {

    public TaskMageAssembly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // When there are no creatures on the battlefield, sacrifice Task Mage Assembly.
        this.addAbility(new TaskMageAssemblyStateTriggeredAbility());

        // {2}: Task Mage Assembly deals 1 damage to target creature. Any player may activate this ability but only any time he or she could cast a sorcery.
        ActivateAsSorceryActivatedAbility ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl("{2}"));
        ability.addTarget(new TargetCreaturePermanent());
        ability.setMayActivate(TargetController.ANY);
        ability.addEffect(new InfoEffect("Any player may activate this ability"));
        this.addAbility(ability);
    }

    public TaskMageAssembly(final TaskMageAssembly card) {
        super(card);
    }

    @Override
    public TaskMageAssembly copy() {
        return new TaskMageAssembly(this);
    }
}

class TaskMageAssemblyStateTriggeredAbility extends StateTriggeredAbility {

    public TaskMageAssemblyStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    public TaskMageAssemblyStateTriggeredAbility(final TaskMageAssemblyStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TaskMageAssemblyStateTriggeredAbility copy() {
        return new TaskMageAssemblyStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getBattlefield().count(new FilterCreaturePermanent(), this.getSourceId(), this.getControllerId(), game) == 0;
    }

    @Override
    public String getRule() {
        return new StringBuilder("When there are no creatures on the battlefield, ").append(super.getRule()).toString() ;
    }

}
