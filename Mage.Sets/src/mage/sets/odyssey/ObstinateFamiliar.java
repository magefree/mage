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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author cbt33, jeffwadsworth (Archmage Ascension)
 */
public class ObstinateFamiliar extends CardImpl<ObstinateFamiliar> {

    public ObstinateFamiliar(UUID ownerId) {
        super(ownerId, 210, "Obstinate Familiar", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{R}");
        this.expansionSetCode = "ODY";
        this.subtype.add("Lizard");

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // If you would draw a card, you may skip that draw instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ObstinateFamiliarReplacementEffect()));
    }

    public ObstinateFamiliar(final ObstinateFamiliar card) {
        super(card);
    }

    @Override
    public ObstinateFamiliar copy() {
        return new ObstinateFamiliar(this);
    }
}

class ObstinateFamiliarReplacementEffect extends ReplacementEffectImpl<ObstinateFamiliarReplacementEffect> {

    public ObstinateFamiliarReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would draw a card, you may skip that draw instead.";
    }

    public ObstinateFamiliarReplacementEffect(final ObstinateFamiliarReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ObstinateFamiliarReplacementEffect copy() {
        return new ObstinateFamiliarReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

@Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent archmage = game.getPermanent(source.getSourceId());
        Player you = game.getPlayer(source.getControllerId());
        if (event.getType() == GameEvent.EventType.DRAW_CARD
                && event.getPlayerId().equals(source.getControllerId())
                && archmage != null
                && you != null
                && you.chooseUse(Outcome.Benefit, "Would you like to skip drawing a card?", game)) {
            return true;
        }
        return false;
    }
    
}