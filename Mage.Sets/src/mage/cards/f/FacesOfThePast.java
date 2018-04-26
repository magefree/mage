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
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2 & L_J
 */
public class FacesOfThePast extends CardImpl {

    public FacesOfThePast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");

        // Whenever a creature dies, tap all untapped creatures that share a creature type with it or untap all tapped creatures that share a creature type with it.
        this.addAbility(new DiesCreatureTriggeredAbility(new FacesOfThePastEffect(), false, false, true));
    }

    public FacesOfThePast(final FacesOfThePast card) {
        super(card);
    }

    @Override
    public FacesOfThePast copy() {
        return new FacesOfThePast(this);
    }
}

class FacesOfThePastEffect extends OneShotEffect {

    public FacesOfThePastEffect() {
        super(Outcome.Benefit);
        this.staticText = "tap all untapped creatures that share a creature type with it or untap all tapped creatures that share a creature type with it";
    }

    public FacesOfThePastEffect(final FacesOfThePastEffect effect) {
        super(effect);
    }

    @Override
    public FacesOfThePastEffect copy() {
        return new FacesOfThePastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetPermanent = (Permanent) game.getLastKnownInformation(this.getTargetPointer().getFirst(game, source), Zone.BATTLEFIELD);
        if (targetPermanent != null) {
            Player controller = game.getPlayer(targetPermanent.getControllerId());
            if (controller != null) {
                if (controller.chooseUse(outcome, "Tap all untapped creatures that share a creature type with " + targetPermanent.getLogName() + "? (Otherwise, untaps all tapped)", source, game)) {
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, game)) {
                        if (!permanent.isTapped() && targetPermanent.shareSubtypes(permanent, game)) {
                            permanent.tap(game);
                        }
                    }
                } else {
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, game)) {
                        if (permanent.isTapped() && targetPermanent.shareSubtypes(permanent, game)) {
                            permanent.untap(game);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}
