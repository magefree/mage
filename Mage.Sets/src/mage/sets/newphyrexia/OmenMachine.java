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
package mage.sets.newphyrexia;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author BetaSteward
 */
public class OmenMachine extends CardImpl {

    public OmenMachine(UUID ownerId) {
        super(ownerId, 148, "Omen Machine", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{6}");
        this.expansionSetCode = "NPH";

        // Players can't draw cards.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OmenMachineEffect()));

        // At the beginning of each player's draw step, that player exiles the top card of his or her library. If it's a land card, the player puts it onto the battlefield. Otherwise, the player casts it without paying its mana cost if able.
        this.addAbility(new BeginningOfDrawTriggeredAbility(new OmenMachineEffect2(), TargetController.ANY, false));
    }

    public OmenMachine(final OmenMachine card) {
        super(card);
    }

    @Override
    public OmenMachine copy() {
        return new OmenMachine(this);
    }
}

class OmenMachineEffect extends ContinuousRuleModifyingEffectImpl {

    public OmenMachineEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral, false, false);
        staticText = "Players can't draw cards";
    }

    public OmenMachineEffect(final OmenMachineEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public OmenMachineEffect copy() {
        return new OmenMachineEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

}

class OmenMachineEffect2 extends OneShotEffect {

    public OmenMachineEffect2() {
        super(Outcome.PlayForFree);
        staticText = "that player exiles the top card of his or her library. If it's a land card, the player puts it onto the battlefield. Otherwise, the player casts it without paying its mana cost if able";
    }

    public OmenMachineEffect2(final OmenMachineEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                player.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.LIBRARY, true);
                if (card.getCardType().contains(CardType.LAND)) {
                    player.putOntoBattlefieldWithInfo(card, game, Zone.EXILED, source.getSourceId());
                }
                else {
                    if (card.getSpellAbility().canChooseTarget(game)) {
                        player.cast(card.getSpellAbility(), game, true);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public OmenMachineEffect2 copy() {
        return new OmenMachineEffect2(this);
    }

}
