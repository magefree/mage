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
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.NameACardEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.effects.common.continuous.LifeTotalCantChangeControllerEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterObject;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public class TeferisProtection extends CardImpl {

    public TeferisProtection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Until your next turn, your life total can't change, and you have protection from everything. All permanents you control phase out. (While they're phased out, they're treated as though they don't exist. They phase in before you untap during your untap step.)
        this.getSpellAbility().addEffect(new LifeTotalCantChangeControllerEffect(Duration.UntilYourNextTurn));
        this.getSpellAbility().addEffect(new TeferisProtectionEffect());
        this.getSpellAbility().addEffect(new TeferisProtectionPhaseOutEffect());

        // Exile Teferi's Protection.
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());
    }

    public TeferisProtection(final TeferisProtection card) {
        super(card);
    }

    @Override
    public TeferisProtection copy() {
        return new TeferisProtection(this);
    }
}

class TeferisProtectionEffect extends OneShotEffect {

    public TeferisProtectionEffect() {
        super(Outcome.Protect);
        staticText = "<br/><br/>You have protection from everything<i>(You can't be targeted, dealt damage, or enchanted by anything.)</i>";
    }

    public TeferisProtectionEffect(final TeferisProtectionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + NameACardEffect.INFO_KEY);
        if (controller != null) {
            FilterObject filter = new FilterObject("the name [everything]");
            filter.add(new NamePredicate("everything"));
            ContinuousEffect effect = new GainAbilityControllerEffect(new ProtectionAbility(filter), Duration.Custom);
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }

    @Override
    public TeferisProtectionEffect copy() {
        return new TeferisProtectionEffect(this);
    }
}

class TeferisProtectionPhaseOutEffect extends OneShotEffect {

    private FilterControlledPermanent permanentsYouControl = new FilterControlledPermanent("all permanents you control");

    public TeferisProtectionPhaseOutEffect() {
        super(Outcome.Benefit);
        this.staticText = "All permanents you control phase out. (While they're phased out, they're treated as though they don't exist. They phase in before you untap during your untap step.)";
    }

    public TeferisProtectionPhaseOutEffect(final TeferisProtectionPhaseOutEffect effect) {
        super(effect);
    }

    @Override
    public TeferisProtectionPhaseOutEffect copy() {
        return new TeferisProtectionPhaseOutEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(permanentsYouControl, controller.getId(), game)) {
                permanent.phaseOut(game);
            }
            return true;
        }
        return false;
    }
}
