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
package mage.sets.odyssey;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author cbt33, Unknown (Glimpse of Nature), LevelX2 (Armadillo Cloak)
 */
public class Spiritualize extends CardImpl<Spiritualize> {

    public Spiritualize(UUID ownerId) {
        super(ownerId, 53, "Spiritualize", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{W}");
        this.expansionSetCode = "ODY";

        this.color.setWhite(true);

        // Until end of turn, whenever target creature deals damage, you gain that much life.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new SpiritualizeTriggeredAbility()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(true));
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public Spiritualize(final Spiritualize card) {
        super(card);
    }

    @Override
    public Spiritualize copy() {
        return new Spiritualize(this);
    }
}

class SpiritualizeTriggeredAbility extends DelayedTriggeredAbility<SpiritualizeTriggeredAbility> {

    public SpiritualizeTriggeredAbility() {
        super(new SpiritualizeEffect(), Duration.EndOfTurn, false);
    }

    public SpiritualizeTriggeredAbility(final SpiritualizeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SpiritualizeTriggeredAbility copy() {
        return new SpiritualizeTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType().equals(GameEvent.EventType.DAMAGED_CREATURE)
                || event.getType().equals(GameEvent.EventType.DAMAGED_PLAYER)
                || event.getType().equals(GameEvent.EventType.DAMAGED_PLANESWALKER)) {
            Permanent target = game.getPermanent(this.getFirstTarget());
            if (target != null && event.getSourceId().equals(target.getId())) {
                for (Effect effect : this.getEffects()) {
                    effect.setValue("damage", event.getAmount());
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever target creature deals damage, " + super.getRule();
    }
}

class SpiritualizeEffect extends OneShotEffect<SpiritualizeEffect> {

    public SpiritualizeEffect() {
        super(Outcome.GainLife);
        this.staticText = "you gain that much life";
    }

    public SpiritualizeEffect(final SpiritualizeEffect effect) {
        super(effect);
    }

    @Override
    public SpiritualizeEffect copy() {
        return new SpiritualizeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = (Integer) getValue("damage");
        if (amount > 0) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                controller.gainLife(amount, game);
                return true;
            }
        }
        return false;
    }
}
