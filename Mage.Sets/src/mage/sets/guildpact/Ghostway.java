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
package mage.sets.guildpact;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class Ghostway extends CardImpl<Ghostway> {

    public Ghostway(UUID ownerId) {
        super(ownerId, 6, "Ghostway", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{2}{W}");
        this.expansionSetCode = "GPT";

        this.color.setWhite(true);

        // Exile each creature you control. Return those cards to the battlefield under their owner's control at the beginning of the next end step.
        this.getSpellAbility().addEffect(new GhostwayEffect());
    }

    public Ghostway(final Ghostway card) {
        super(card);
    }

    @Override
    public Ghostway copy() {
        return new Ghostway(this);
    }
}

class GhostwayEffect extends OneShotEffect<GhostwayEffect> {

    private static final FilterPermanent filter = new FilterPermanent("each creature you control");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public GhostwayEffect() {
        super(Constants.Outcome.Neutral);
        staticText = "Exile each creature you control. Return those cards to the battlefield under their owner's control at the beginning of the next end step";
    }

    public GhostwayEffect(final GhostwayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID exileId = source.getSourceId();
        if (exileId != null) {
            for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
                if (creature != null) {
                    if (creature.moveToExile(source.getSourceId(), "Ghostway Exile", source.getId(), game)) {
                        AtEndOfTurnDelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(new ReturnFromExileEffect(source.getSourceId(), Constants.Zone.BATTLEFIELD, true));
                        delayedAbility.setSourceId(source.getSourceId());
                        delayedAbility.setControllerId(source.getControllerId());
                        game.addDelayedTriggeredAbility(delayedAbility);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public GhostwayEffect copy() {
        return new GhostwayEffect(this);
    }
}