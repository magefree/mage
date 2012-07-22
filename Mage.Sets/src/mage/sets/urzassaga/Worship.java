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
package mage.sets.urzassaga;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author magenoxx_at_gmail.com

 */
public class Worship extends CardImpl<Worship> {

    public Worship(UUID ownerId) {
        super(ownerId, 57, "Worship", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");
        this.expansionSetCode = "USG";

        this.color.setWhite(true);

        // If you control a creature, damage that would reduce your life total to less than 1 reduces it to 1 instead.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new WorshipReplacementEffect()));
    }

    public Worship(final Worship card) {
        super(card);
    }

    @Override
    public Worship copy() {
        return new Worship(this);
    }
}

class WorshipReplacementEffect extends ReplacementEffectImpl<WorshipReplacementEffect> {

    public WorshipReplacementEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Benefit);
        staticText = "If you control a creature, damage that would reduce your life total to less than 1 reduces it to 1 instead";
    }

    public WorshipReplacementEffect(final WorshipReplacementEffect effect) {
        super(effect);
    }

    @Override
    public WorshipReplacementEffect copy() {
        return new WorshipReplacementEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType().equals(GameEvent.EventType.DAMAGE_CAUSES_LIFE_LOSS)) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent == null) {
                permanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Constants.Zone.BATTLEFIELD);
            }
            if (permanent != null) {
                Player controller = game.getPlayer(permanent.getControllerId());
                if (controller != null
                        && (controller.getLife() - event.getAmount()) < 1
                        && event.getPlayerId().equals(controller.getId())
                        && game.getBattlefield().count(new FilterControlledCreaturePermanent(), event.getPlayerId(), game) > 0
                       ) {
                    event.setAmount(controller.getLife() - 1);
                }
            }
        }

        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return false;
    }

}
