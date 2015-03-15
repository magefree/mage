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
package mage.sets.futuresight;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.constants.Rarity;
import mage.constants.CardType;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.filter.FilterSpell;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.abilities.Ability;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.events.GameEvent.EventType;

/**
 * @author duncant
 */
public class VeilstoneAmulet extends CardImpl {

    public VeilstoneAmulet(UUID ownerId) {
        super(ownerId, 166, "Veilstone Amulet", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "FUT";

        // Whenever you cast a spell, creatures you control can't be the targets of spells or abilities your opponents control this turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new VeilstoneAmuletEffect(), new FilterSpell("a spell"), false));
    }

    public VeilstoneAmulet(final VeilstoneAmulet card) {
        super(card);
    }

    @Override
    public VeilstoneAmulet copy() {
        return new VeilstoneAmulet(this);
    }
}

// Veilstone Amulet's effect is strange. It effects all creatures you control,
// even if they entered the battlefield after the ability resolved. It modifies
// the rules of the game until end of turn.
class VeilstoneAmuletEffect extends ContinuousRuleModifyingEffectImpl {

    public VeilstoneAmuletEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "creatures you control can't be the targets of spells or abilities your opponents control this turn";
    }

    public VeilstoneAmuletEffect(final VeilstoneAmuletEffect effect) {
        super(effect);
    }

    @Override
    public VeilstoneAmuletEffect copy() {
        return new VeilstoneAmuletEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TARGET;
    }

    @Override
    public boolean applies(GameEvent event, Ability ability, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            if (permanent.getCardType().contains(CardType.CREATURE) &&
                permanent.getControllerId().equals(ability.getControllerId()) &&
                game.getPlayer(ability.getControllerId()).hasOpponent(event.getPlayerId(), game)) {
                return true;
            }
        }
        return false;
    }
}
