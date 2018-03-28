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
package mage.cards.m;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedThisTurnWatcher;
import mage.watchers.common.PermanentsEnteredBattlefieldWatcher;

/**
 *
 * @author jeffwadsworth
 */
public class MadDog extends CardImpl {

    public MadDog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HOUND);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your end step, if Mad Dog didn't attack or come under your control this turn, sacrifice it.
        Ability ability = new ConditionalTriggeredAbility(new BeginningOfEndStepTriggeredAbility(new SacrificeSourceEffect(), TargetController.YOU, false), MadDogCondition.instance, "At the beginning of your end step, if {this} didn't attack or come under your control this turn, sacrifice it");
        ability.addWatcher(new AttackedThisTurnWatcher());
        ability.addWatcher(new PermanentsEnteredBattlefieldWatcher());
        this.addAbility(ability);

    }

    public MadDog(final MadDog card) {
        super(card);
    }

    @Override
    public MadDog copy() {
        return new MadDog(this);
    }
}

enum MadDogCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent madDog = game.getPermanent(source.getSourceId());
        PermanentsEnteredBattlefieldWatcher watcher = (PermanentsEnteredBattlefieldWatcher) game.getState().getWatchers().get(PermanentsEnteredBattlefieldWatcher.class.getSimpleName());
        AttackedThisTurnWatcher watcher2 = (AttackedThisTurnWatcher) game.getState().getWatchers().get(AttackedThisTurnWatcher.class.getSimpleName());
        if (watcher != null
                && watcher2 != null
                && madDog != null) {
            // For some reason, compare did not work when checking the lists.  Thus the interation.
            List<Permanent> permanents = watcher.getThisTurnEnteringPermanents(source.getControllerId());
            if (!permanents.stream().noneMatch((p) -> (p.getId().equals(madDog.getId())))) {
                return false;
            }
            Set<MageObjectReference> mor = watcher2.getAttackedThisTurnCreatures();
            if (!mor.stream().noneMatch((m) -> (m.getPermanent(game).equals(madDog)))) {
                return false;
            }
            return true; // Mad Dog did not come into play this turn nor did he attack this turn.  Sacrifice the hound.
        }
        return false;
    }
}
