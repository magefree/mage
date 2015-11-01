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
package mage.sets.fatereforged;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.PutTokenOntoBattlefieldCopyTargetEffect;
import mage.abilities.keyword.DashAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class FlamerushRider extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target attacking creature");

    static {
        filter.add(new AnotherPredicate());
        filter.add(new AttackingPredicate());
    }

    public FlamerushRider(UUID ownerId) {
        super(ownerId, 99, "Flamerush Rider", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.expansionSetCode = "FRF";
        this.subtype.add("Human");
        this.subtype.add("Warrior");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Flamerush Rider attacks, put a token onto the battlefield tapped and attacking that's a copy of another target attacking creature. Exile the token at end of combat.
        Ability ability = new AttacksTriggeredAbility(new FlamerushRiderEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Dash {2}{R}{R}
        this.addAbility(new DashAbility(this, "{2}{R}{R}"));
    }

    public FlamerushRider(final FlamerushRider card) {
        super(card);
    }

    @Override
    public FlamerushRider copy() {
        return new FlamerushRider(this);
    }
}

class FlamerushRiderEffect extends OneShotEffect {

    public FlamerushRiderEffect() {
        super(Outcome.Copy);
        this.staticText = "put a token onto the battlefield tapped and attacking that's a copy of another target attacking creature. Exile the token at end of combat";
    }

    public FlamerushRiderEffect(final FlamerushRiderEffect effect) {
        super(effect);
    }

    @Override
    public FlamerushRiderEffect copy() {
        return new FlamerushRiderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if (controller != null && permanent != null) {
            PutTokenOntoBattlefieldCopyTargetEffect effect = new PutTokenOntoBattlefieldCopyTargetEffect(source.getControllerId(), null, true, 1, true, true);
            effect.setTargetPointer(new FixedTarget(permanent, game));
            effect.apply(game, source);
            for (Permanent addedToken : effect.getAddedPermanent()) {
                Effect exileEffect = new ExileTargetEffect();
                exileEffect.setTargetPointer(new FixedTarget(addedToken, game));
                new CreateDelayedTriggeredAbilityEffect(new AtTheEndOfCombatDelayedTriggeredAbility(exileEffect), false).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
