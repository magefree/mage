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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CipherEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class Voidwalk extends CardImpl<Voidwalk> {

    public Voidwalk(UUID ownerId) {
        super(ownerId, 55, "Voidwalk", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{3}{U}");
        this.expansionSetCode = "GTC";

        this.color.setBlue(true);

        // Exile target creature. Return it to the battlefield under its owner's control at the beginning of the next end step.
        this.getSpellAbility().addEffect(new VoidwalkEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Cipher
        this.getSpellAbility().addEffect(new CipherEffect());
    }

    public Voidwalk(final Voidwalk card) {
        super(card);
    }

    @Override
    public Voidwalk copy() {
        return new Voidwalk(this);
    }
}

class VoidwalkEffect extends OneShotEffect<VoidwalkEffect> {

    private static final String effectText = "Exile target creature. Return it to the battlefield under its owner's control at the beginning of the next end step";

    VoidwalkEffect() {
        super(Constants.Outcome.Benefit);
        staticText = effectText;
    }

    VoidwalkEffect(VoidwalkEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (getTargetPointer().getFirst(game, source) != null) {
            Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                if (permanent.moveToExile(source.getSourceId(), "Voidwalk", source.getSourceId(), game)) {
                    if (card != null) {
                        AtEndOfTurnDelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(new ReturnFromExileEffect(source.getSourceId(), Constants.Zone.BATTLEFIELD));
                        delayedAbility.setSourceId(source.getSourceId());
                        delayedAbility.setControllerId(card.getOwnerId());
                        game.addDelayedTriggeredAbility(delayedAbility);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public VoidwalkEffect copy() {
        return new VoidwalkEffect(this);
    }
}