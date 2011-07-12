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

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 * @author Loki
 */
public class NornsAnnex extends CardImpl<NornsAnnex> {

    public NornsAnnex(UUID ownerId) {
        super(ownerId, 17, "Norn's Annex", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}{WP}{WP}");
        this.expansionSetCode = "NPH";
        this.color.setWhite(true);
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new NornsAnnexReplacementEffect()));
    }

    public NornsAnnex(final NornsAnnex card) {
        super(card);
    }

    @Override
    public NornsAnnex copy() {
        return new NornsAnnex(this);
    }

}

class NornsAnnexReplacementEffect extends ReplacementEffectImpl<NornsAnnexReplacementEffect> {

    private static final String effectText = "Creatures can't attack you unless their controller pays {WP} for each creature he or she controls that's attacking you";

    NornsAnnexReplacementEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Benefit);
    }

    NornsAnnexReplacementEffect(NornsAnnexReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DECLARE_ATTACKER) {
            Player player = game.getPlayer(event.getPlayerId());
            if (player != null && event.getTargetId().equals(source.getControllerId())) {
                ManaCostsImpl propagandaTax = new ManaCostsImpl("{WP}");
                if (propagandaTax.canPay(source.getSourceId(), event.getPlayerId(), game) &&
                        player.chooseUse(Constants.Outcome.Benefit, "Pay {WP} to declare attacker?", game)) {
                    propagandaTax.pay(game, this.getId(), event.getPlayerId(), false);

                    if (propagandaTax.isPaid()) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DECLARE_ATTACKER && event.getTargetId().equals(source.getControllerId())) {
            return true;
        }
        return false;
    }

    @Override
    public NornsAnnexReplacementEffect copy() {
        return new NornsAnnexReplacementEffect(this);
    }

    @Override
    public String getText(Ability source) {
        return effectText;
    }
}


