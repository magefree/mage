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
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.effects.common.continuous.LifeTotalCantChangeControllerEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
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
        this.getSpellAbility().addEffect(new LifeTotalCantChangeControllerEffect(Duration.UntilYourNextTurn)
                .setText("Until your next turn, your life total can't change"));
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

    /**
     * 25.08.2017 The following rulings focus on the “protection from” keyword
     *
     * 25.08.2017 If a player has protection from everything, it means three
     * things: 1) All damage that would be dealt to that player is prevented. 2)
     * Auras can’t be attached to that player. 3) That player can’t be the
     * target of spells or abilities.
     *
     * 25.08.2017 Nothing other than the specified events are prevented or
     * illegal. An effect that doesn’t target you could still cause you to
     * discard cards, for example. Creatures can still attack you while you have
     * protection from everything, although combat damage that they would deal
     * to you will be prevented.
     *
     * 25.08.2017 Gaining protection from everything causes a spell or ability
     * on the stack to have an illegal target if it targets you. As a spell or
     * ability tries to resolve, if all its targets are illegal, that spell or
     * ability is countered and none of its effects happen, including effects
     * unrelated to the target. If at least one target is still legal, the spell
     * or ability does as much as it can to the remaining legal targets, and its
     * other effects still happen.
     */
    public TeferisProtectionEffect() {
        super(Outcome.Protect);
        staticText = ", and you have protection from everything";
    }

    public TeferisProtectionEffect(final TeferisProtectionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            game.addEffect(new GainAbilityControllerEffect(new TeferisProtectionAbility(), Duration.UntilYourNextTurn), source);
            return true;
        }
        return false;
    }

    @Override
    public TeferisProtectionEffect copy() {
        return new TeferisProtectionEffect(this);
    }
}

class TeferisProtectionAbility extends ProtectionAbility {

    public TeferisProtectionAbility() {
        super(new FilterCard("everything"));
    }

    public TeferisProtectionAbility(final TeferisProtectionAbility ability) {
        super(ability);
    }

    @Override
    public TeferisProtectionAbility copy() {
        return new TeferisProtectionAbility(this);
    }

    @Override
    public String getRule() {
        return "Protection from everything";
    }

    @Override
    public boolean canTarget(MageObject source, Game game) {
        return false;
    }
}

class TeferisProtectionPhaseOutEffect extends OneShotEffect {

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
            for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_CONTROLLED_PERMANENT, controller.getId(), game)) {
                permanent.phaseOut(game);
            }
            return true;
        }
        return false;
    }
}
