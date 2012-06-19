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

package mage.sets.riseoftheeldrazi;

import java.util.UUID;

import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;


/**
 *
 * @author Loki
 */
public class LinvalaKeeperOfSilence extends CardImpl<LinvalaKeeperOfSilence> {

    public LinvalaKeeperOfSilence (UUID ownerId) {
        super(ownerId, 33, "Linvala, Keeper of Silence", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.expansionSetCode = "ROE";
        this.supertype.add("Legendary");
        this.subtype.add("Angel");
        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LinvalaKeeperOfSilenceEffect()));
    }

    public LinvalaKeeperOfSilence (final LinvalaKeeperOfSilence card) {
        super(card);
    }

    @Override
    public LinvalaKeeperOfSilence copy() {
        return new LinvalaKeeperOfSilence(this);
    }
}

class LinvalaKeeperOfSilenceEffect extends ReplacementEffectImpl<LinvalaKeeperOfSilenceEffect> {
    LinvalaKeeperOfSilenceEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Activated abilities of creatures your opponents control can't be activated";
    }

    LinvalaKeeperOfSilenceEffect(final LinvalaKeeperOfSilenceEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent p = game.getPermanent(event.getSourceId());
        if ( event.getType() == GameEvent.EventType.ACTIVATE_ABILITY && game.getOpponents(source.getControllerId()).contains(event.getPlayerId())
                && p != null && p.getCardType().contains(CardType.CREATURE)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public LinvalaKeeperOfSilenceEffect copy() {
        return new LinvalaKeeperOfSilenceEffect(this);
    }

}