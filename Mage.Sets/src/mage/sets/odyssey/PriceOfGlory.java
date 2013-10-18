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
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author cbt33, Loki (Heartbeat of Spring)
 */
public class PriceOfGlory extends CardImpl<PriceOfGlory> {

    public PriceOfGlory(UUID ownerId) {
        super(ownerId, 214, "Price of Glory", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        this.expansionSetCode = "ODY";

        this.color.setRed(true);

        // Whenever a player taps a land for mana, if it's not that player's turn, destroy that land.
        this.addAbility(new PriceOfGloryAbility());
    }

    public PriceOfGlory(final PriceOfGlory card) {
        super(card);
    }

    @Override
    public PriceOfGlory copy() {
        return new PriceOfGlory(this);
    }
}

class PriceOfGloryAbility extends TriggeredAbilityImpl<PriceOfGloryAbility> {

    private static final String staticText = "Whenever a player taps a land for mana, if it's not that player's turn, destroy that land.";

    public PriceOfGloryAbility() {
        super(Zone.BATTLEFIELD, new PriceOfGloryEffect());
    }

    public PriceOfGloryAbility(PriceOfGloryAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TAPPED_FOR_MANA) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent == null) {
                permanent = (Permanent) game.getLastKnownInformation(event.getSourceId(), Zone.BATTLEFIELD);
            }
            if (permanent != null && permanent.getCardType().contains(CardType.LAND)) {
                getEffects().get(0).setTargetPointer(new FixedTarget(permanent.getId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public PriceOfGloryAbility copy() {
        return new PriceOfGloryAbility(this);
    }

    @Override
    public String getRule() {
        return staticText;
    }
}

class PriceOfGloryEffect extends OneShotEffect<PriceOfGloryEffect> {

    public PriceOfGloryEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "if it's not that player's turn, destroy that land.";
    }

    public PriceOfGloryEffect(final PriceOfGloryEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent land = game.getPermanent(this.targetPointer.getFirst(game, source));
        if (land != null && !land.getControllerId().equals(game.getActivePlayerId())) {
            land.destroy(source.getSourceId(), game, false);
            return true;
        }
        return false;
    }

    @Override
    public PriceOfGloryEffect copy() {
        return new PriceOfGloryEffect(this);
    }
}
