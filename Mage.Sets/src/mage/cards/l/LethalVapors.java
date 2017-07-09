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
package mage.cards.l;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.turn.SkipNextTurnSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public class LethalVapors extends CardImpl {

    public LethalVapors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // Whenever a creature enters the battlefield, destroy it.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD,
                new DestroyTargetEffect().setText("destroy it"),
                StaticFilters.FILTER_PERMANENT_A_CREATURE,
                false, SetTargetPointer.PERMANENT, null));

        // {0}: Destroy Lethal Vapors. You skip your next turn. Any player may activate this ability.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroySourceEffect(), new ManaCostsImpl("{0}"));
        ability.setMayActivate(TargetController.ANY);
        ability.addEffect(new SkipNextTurnSourceEffect());
        ability.addEffect(new InfoEffect("Any player may activate this ability"));
        this.addAbility(ability);
    }

    public LethalVapors(final LethalVapors card) {
        super(card);
    }

    @Override
    public LethalVapors copy() {
        return new LethalVapors(this);
    }
}
