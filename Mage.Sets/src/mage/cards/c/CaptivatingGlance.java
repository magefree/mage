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
package mage.cards.c;

import java.util.UUID;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ClashEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class CaptivatingGlance extends CardImpl {

    public CaptivatingGlance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add("Aura");

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // At the beginning of your end step, clash with an opponent. If you win, gain control of enchanted creature. Otherwise, that player gains control of enchanted creature.
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(new CaptivatingGlanceEffect(), false));

    }

    public CaptivatingGlance(final CaptivatingGlance card) {
        super(card);
    }

    @Override
    public CaptivatingGlance copy() {
        return new CaptivatingGlance(this);
    }
}

class CaptivatingGlanceEffect extends OneShotEffect {

    public CaptivatingGlanceEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "clash with an opponent. If you win, gain control of enchanted creature. Otherwise, that player gains control of enchanted creature";
    }

    public CaptivatingGlanceEffect(final CaptivatingGlanceEffect effect) {
        super(effect);
    }

    @Override
    public CaptivatingGlanceEffect copy() {
        return new CaptivatingGlanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        final boolean clashResult;
        Player controller = game.getPlayer(source.getControllerId());
        Permanent captivatingGlance = game.getPermanent(source.getSourceId());
        if (controller != null
                && captivatingGlance != null) {
            Permanent enchantedCreature = game.getPermanent(captivatingGlance.getAttachedTo());
            clashResult = ClashEffect.getInstance().apply(game, source);
            if (enchantedCreature != null) {
                if (clashResult) {
                    ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, false, controller.getId());
                    effect.setTargetPointer(new FixedTarget(enchantedCreature.getId()));
                    game.addEffect(effect, source);
                } else {
                    Player opponentWhomControllerClashedWith = game.getPlayer(targetPointer.getFirst(game, source));
                    if (opponentWhomControllerClashedWith != null) {
                        ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, false, opponentWhomControllerClashedWith.getId());
                        effect.setTargetPointer(new FixedTarget(enchantedCreature.getId()));
                        game.addEffect(effect, source);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
