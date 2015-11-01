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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.abilityword.StriveAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.PutTokenOntoBattlefieldCopyTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class Twinflame extends CardImpl {

    public Twinflame(UUID ownerId) {
        super(ownerId, 115, "Twinflame", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{1}{R}");
        this.expansionSetCode = "JOU";

        // Strive - Twinflame costs 2R more to cast for each target beyond the first.
        this.addAbility(new StriveAbility("{2}{R}"));
        // Choose any number of target creatures you control. For each of them, put a token that's a copy of that creature onto the battlefield. Those tokens have haste. Exile them at the beginning of the next end step.
        this.getSpellAbility().addEffect(new TwinflameCopyEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, new FilterControlledCreaturePermanent(), false));

    }

    public Twinflame(final Twinflame card) {
        super(card);
    }

    @Override
    public Twinflame copy() {
        return new Twinflame(this);
    }
}

class TwinflameCopyEffect extends OneShotEffect {

    public TwinflameCopyEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Choose any number of target creatures you control. For each of them, put a token that's a copy of that creature onto the battlefield. Those tokens have haste. Exile them at the beginning of the next end step";
    }

    public TwinflameCopyEffect(final TwinflameCopyEffect effect) {
        super(effect);
    }

    @Override
    public TwinflameCopyEffect copy() {
        return new TwinflameCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID creatureId : this.getTargetPointer().getTargets(game, source)) {
                Permanent creature = game.getPermanentOrLKIBattlefield(creatureId);
                if (creature != null) {
                    PutTokenOntoBattlefieldCopyTargetEffect effect = new PutTokenOntoBattlefieldCopyTargetEffect(source.getControllerId(), null, true);
                    effect.setTargetPointer(new FixedTarget(creature, game));
                    effect.apply(game, source);
                    for (Permanent addedToken : effect.getAddedPermanent()) {
                        ExileTargetEffect exileEffect = new ExileTargetEffect();
                        exileEffect.setTargetPointer(new FixedTarget(addedToken, game));
                        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
                        delayedAbility.setSourceId(source.getSourceId());
                        delayedAbility.setControllerId(source.getControllerId());
                        delayedAbility.setSourceObject(source.getSourceObject(game), game);
                        game.addDelayedTriggeredAbility(delayedAbility);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
