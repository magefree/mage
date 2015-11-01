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
package mage.sets.fifthedition;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class Stasis extends CardImpl {

    public Stasis(UUID ownerId) {
        super(ownerId, 127, "Stasis", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        this.expansionSetCode = "5ED";

        // Players skip their untap steps.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipUntapStepEffect()));

        // At the beginning of your upkeep, sacrifice Stasis unless you pay {U}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl("{U}")), TargetController.YOU, false));

    }

    public Stasis(final Stasis card) {
        super(card);
    }

    @Override
    public Stasis copy() {
        return new Stasis(this);
    }
}

class SkipUntapStepEffect extends ContinuousRuleModifyingEffectImpl {

    public SkipUntapStepEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral, false, false);
        staticText = "Players skip their uptap steps";
    }

    public SkipUntapStepEffect(final SkipUntapStepEffect effect) {
        super(effect);
    }

    @Override
    public SkipUntapStepEffect copy() {
        return new SkipUntapStepEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        return event.getType() == GameEvent.EventType.UNTAP_STEP
                && controller != null
                && controller.getInRange().contains(event.getPlayerId());
    }
}
