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
package mage.sets.thedark;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EmptyToken;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public class DanceOfMany extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature");

    static {
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public DanceOfMany(UUID ownerId) {
        super(ownerId, 21, "Dance of Many", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{U}{U}");
        this.expansionSetCode = "DRK";

        // When Dance of Many enters the battlefield, put a token that's a copy of target nontoken creature onto the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DanceOfManyCreateTokenCopyEffect(), false);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // When Dance of Many leaves the battlefield, exile the token.
        // When the token leaves the battlefield, sacrifice Dance of Many.
        Ability ability2 = new LeavesBattlefieldTriggeredAbility(new DanceOfManyExileTokenEffect(), false);
        ability2.addEffect(new InfoEffect("When the token leaves the battlfield, sacrifice {this}"));
        this.addAbility(ability2);

        // At the beginning of your upkeep, sacrifice Dance of Many unless you pay {U}{U}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl("{U}{U}")), TargetController.YOU, false));

    }

    public DanceOfMany(final DanceOfMany card) {
        super(card);
    }

    @Override
    public DanceOfMany copy() {
        return new DanceOfMany(this);
    }
}

class DanceOfManyCreateTokenCopyEffect extends OneShotEffect {

    public DanceOfManyCreateTokenCopyEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "put a token that's a copy of target nontoken creature onto the battlefield";
    }

    public DanceOfManyCreateTokenCopyEffect(final DanceOfManyCreateTokenCopyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            permanent = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        }
        if (permanent != null) {
            EmptyToken token = new EmptyToken();
            CardUtil.copyTo(token).from(permanent);
            token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
            Permanent permanentToken = game.getPermanent(token.getLastAddedToken());
            game.getState().setValue(source.getSourceId() + "_token", permanentToken);
            Effect sacrificeEffect = new SacrificeTargetEffect("sacrifice Dance of Many");
            sacrificeEffect.setTargetPointer(new FixedTarget(game.getPermanent(source.getSourceId()).getId()));
            LeavesBattlefieldTriggeredAbility triggerAbility = new LeavesBattlefieldTriggeredAbility(sacrificeEffect, false);
            ContinuousEffect effect = new GainAbilityTargetEffect(triggerAbility, Duration.WhileOnBattlefield);
            effect.setTargetPointer(new FixedTarget(token.getLastAddedToken()));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }

    @Override
    public DanceOfManyCreateTokenCopyEffect copy() {
        return new DanceOfManyCreateTokenCopyEffect(this);
    }
}

class DanceOfManyExileTokenEffect extends OneShotEffect {

    public DanceOfManyExileTokenEffect() {
        super(Outcome.Removal);
        staticText = "exile the token";
    }

    public DanceOfManyExileTokenEffect(final DanceOfManyExileTokenEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent tokenPermanent = (Permanent) game.getState().getValue(source.getSourceId() + "_token");
        if (tokenPermanent != null) {
            Effect effect = new ExileTargetEffect();
            effect.setTargetPointer(new FixedTarget(tokenPermanent.getId()));
            return effect.apply(game, source);
        }
        return false;
    }

    @Override
    public DanceOfManyExileTokenEffect copy() {
        return new DanceOfManyExileTokenEffect(this);
    }
}
