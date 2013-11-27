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
package mage.sets.commander2013;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class ActOfAuthority extends CardImpl<ActOfAuthority> {

    private static final FilterPermanent filter = new FilterPermanent("artifact or enchantment");
    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT), new CardTypePredicate(CardType.ENCHANTMENT)));
    }

    public ActOfAuthority(UUID ownerId) {
        super(ownerId, 1, "Act of Authority", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");
        this.expansionSetCode = "C13";

        this.color.setWhite(true);

        // When Act of Authority enters the battlefield, you may exile target artifact or enchantment.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect(), true);
        ability.addTarget(new TargetPermanent(filter, true));
        this.addAbility(ability);
        // At the beginning of your upkeep, you may exile target artifact or enchantment. If you do, its controller gains control of Act of Authority.
        ability = new BeginningOfUpkeepTriggeredAbility(new ActOfAuthorityEffect(), TargetController.YOU, true);
        ability.addTarget(new TargetPermanent(filter, true));
        this.addAbility(ability);
    }

    public ActOfAuthority(final ActOfAuthority card) {
        super(card);
    }

    @Override
    public ActOfAuthority copy() {
        return new ActOfAuthority(this);
    }
}

class ActOfAuthorityEffect extends OneShotEffect<ActOfAuthorityEffect> {

    public ActOfAuthorityEffect() {
        super(Outcome.Exile);
        this.staticText = "you may exile target artifact or enchantment. If you do, its controller gains control of {this}";
    }

    public ActOfAuthorityEffect(final ActOfAuthorityEffect effect) {
        super(effect);
    }

    @Override
    public ActOfAuthorityEffect copy() {
        return new ActOfAuthorityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetPermanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (targetPermanent != null && new ExileTargetEffect().apply(game, source)) {
            ContinuousEffect effect = new ActOfAuthorityGainControlEffect(Duration.Custom, targetPermanent.getControllerId());
            effect.setTargetPointer(new FixedTarget(source.getSourceId()));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}

class ActOfAuthorityGainControlEffect extends ContinuousEffectImpl<ActOfAuthorityGainControlEffect> {

    UUID controller;

    public ActOfAuthorityGainControlEffect(Duration duration, UUID controller) {
        super(duration, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.controller = controller;
    }

    public ActOfAuthorityGainControlEffect(final ActOfAuthorityGainControlEffect effect) {
        super(effect);
        this.controller = effect.controller;
    }

    @Override
    public ActOfAuthorityGainControlEffect copy() {
        return new ActOfAuthorityGainControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (targetPointer != null) {
            permanent = game.getPermanent(targetPointer.getFirst(game, source));
        }
        if (permanent != null) {
            return permanent.changeControllerId(controller, game);
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Gain control of {this}";
    }
}
