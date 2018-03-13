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
package mage.cards.v;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.AttackedThisTurnWatcher;
import mage.watchers.common.BlockedThisTurnWatcher;

/**
 *
 * @author jeffwadsworth
 */
public class VizierOfDeferment extends CardImpl {

    public VizierOfDeferment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Vizier of Deferment enters the battlefield, you may exile target creature if it attacked or blocked this turn. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new EntersBattlefieldTriggeredAbility(new VizierOfDefermentEffect(), true);
        ability.addWatcher(new AttackedThisTurnWatcher());
        ability.addWatcher(new BlockedThisTurnWatcher());
        this.addAbility(ability);

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof EntersBattlefieldTriggeredAbility) {
            List<PermanentIdPredicate> creaturesThatCanBeTargeted = new ArrayList<>();
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that attacked or blocked this turn.");
            AttackedThisTurnWatcher watcherAttacked = (AttackedThisTurnWatcher) game.getState().getWatchers().get(AttackedThisTurnWatcher.class.getSimpleName());
            BlockedThisTurnWatcher watcherBlocked = (BlockedThisTurnWatcher) game.getState().getWatchers().get(BlockedThisTurnWatcher.class.getSimpleName());
            if (watcherAttacked != null) {
                for (MageObjectReference mor : watcherAttacked.getAttackedThisTurnCreatures()) {
                    Permanent permanent = mor.getPermanent(game);
                    if (permanent != null) {
                        creaturesThatCanBeTargeted.add(new PermanentIdPredicate(permanent.getId()));
                    }
                }
                if (watcherBlocked != null) {
                    for (MageObjectReference mor : watcherBlocked.getBlockedThisTurnCreatures()) {
                        Permanent permanent = mor.getPermanent(game);
                        if (permanent != null) {
                            creaturesThatCanBeTargeted.add(new PermanentIdPredicate(permanent.getId()));
                        }
                    }
                }
                filter.add(Predicates.or(creaturesThatCanBeTargeted));
                ability.getTargets().clear();
                ability.addTarget(new TargetCreaturePermanent(filter));
            }
        }
    }

    public VizierOfDeferment(final VizierOfDeferment card) {
        super(card);
    }

    @Override
    public VizierOfDeferment copy() {
        return new VizierOfDeferment(this);
    }
}

class VizierOfDefermentEffect extends OneShotEffect {

    public VizierOfDefermentEffect() {
        super(Outcome.Detriment);
        staticText = "you may exile target creature if it attacked or blocked this turn. Return that card to the battlefield under its owner's control at the beginning of the next end step";
    }

    public VizierOfDefermentEffect(final VizierOfDefermentEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null
                && permanent != null
                && sourcePermanent != null) {
            if (controller.moveCardToExileWithInfo(permanent, source.getSourceId(), sourcePermanent.getIdName(), source.getSourceId(), game, Zone.BATTLEFIELD, true)) {
                Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect();
                effect.setText("Return that card to the battlefield under its owner's control at the beginning of the next end step");
                effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
                return true;
            }
        }
        return false;
    }

    @Override
    public VizierOfDefermentEffect copy() {
        return new VizierOfDefermentEffect(this);
    }

}
