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
package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author North
 */
public class BloodTribute extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("an untapped Vampire you control");

    static {
        filter.add(Predicates.not(new TappedPredicate()));
        filter.add(new SubtypePredicate(SubType.VAMPIRE));
    }

    public BloodTribute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}{B}");

        // Kicker - Tap an untapped Vampire you control.
        this.addAbility(new KickerAbility(new TapTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, true))));

        // Target opponent loses half his or her life, rounded up.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new BloodTributeLoseLifeEffect());

        // If Blood Tribute was kicked, you gain life equal to the life lost this way.
        Effect effect = new ConditionalOneShotEffect(
                new BloodTributeGainLifeEffect(),
                KickedCondition.instance,
                "If {this} was kicked, you gain life equal to the life lost this way");
        this.getSpellAbility().addEffect(effect);
    }

    public BloodTribute(final BloodTribute card) {
        super(card);
    }

    @Override
    public BloodTribute copy() {
        return new BloodTribute(this);
    }
}

class BloodTributeLoseLifeEffect extends OneShotEffect {

    public BloodTributeLoseLifeEffect() {
        super(Outcome.Damage);
        this.staticText = "Target opponent loses half his or her life, rounded up";
    }

    public BloodTributeLoseLifeEffect(final BloodTributeLoseLifeEffect effect) {
        super(effect);
    }

    @Override
    public BloodTributeLoseLifeEffect copy() {
        return new BloodTributeLoseLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            Integer amount = (int) Math.ceil(player.getLife() / 2f);
            if (amount > 0) {
                player.loseLife(amount, game, false);
                game.getState().setValue(source.getSourceId().toString() + "_BloodTribute", amount);
                return true;
            }
        }
        return false;
    }
}

class BloodTributeGainLifeEffect extends OneShotEffect {

    public BloodTributeGainLifeEffect() {
        super(Outcome.GainLife);
        this.staticText = "If Blood Tribute was kicked, you gain life equal to the life lost this way";
    }

    public BloodTributeGainLifeEffect(final BloodTributeGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public BloodTributeGainLifeEffect copy() {
        return new BloodTributeGainLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Integer amount = (Integer) game.getState().getValue(source.getSourceId().toString() + "_BloodTribute");
            if (amount != null && amount > 0) {
                player.gainLife(amount, game);
                return true;
            }
        }
        return false;
    }
}
