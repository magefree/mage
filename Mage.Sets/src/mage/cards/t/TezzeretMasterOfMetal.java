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

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Styxo
 */
public class TezzeretMasterOfMetal extends CardImpl {

    public TezzeretMasterOfMetal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{U}{B}");
        this.subtype.add("Tezzeret");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(5));

        // +1: Reveal cards from the top of your library until you reveal an artifact card. Put that card into your hand and the rest on the bottom of your library in a random order.
        this.addAbility(new LoyaltyAbility(new RevealCardsFromLibraryUntilEffect(new FilterArtifactCard()), 1));

        // -3: Target opponent loses life equal to the number of artifacts you control.
        Ability ability = new LoyaltyAbility(new LoseLifeTargetEffect(new PermanentsOnBattlefieldCount(new FilterControlledArtifactPermanent())), -3);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // -8: Gain control of all artifacts and creatures target opponent controls.
        ability = new LoyaltyAbility(new TezzeretMasterOfMetalEffect(), -8);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public TezzeretMasterOfMetal(final TezzeretMasterOfMetal card) {
        super(card);
    }

    @Override
    public TezzeretMasterOfMetal copy() {
        return new TezzeretMasterOfMetal(this);
    }
}

class TezzeretMasterOfMetalEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("artifacts and creatures");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.CREATURE), new CardTypePredicate(CardType.ARTIFACT)));
    }

    public TezzeretMasterOfMetalEffect() {
        super(Outcome.GainControl);
        this.staticText = "Gain control of all artifacts and creatures target opponent controls";
    }

    public TezzeretMasterOfMetalEffect(final TezzeretMasterOfMetalEffect effect) {
        super(effect);
    }

    @Override
    public TezzeretMasterOfMetalEffect copy() {
        return new TezzeretMasterOfMetalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(filter, targetPointer.getFirst(game, source), game);
        for (Permanent permanent : permanents) {
            ContinuousEffect effect = new TibaltTheFiendBloodedControlEffect(source.getControllerId());
            effect.setTargetPointer(new FixedTarget(permanent.getId()));
            game.addEffect(effect, source);
        }
        return true;
    }
}

class TezzeretMasterOfMetalControlEffect extends ContinuousEffectImpl {

    private final UUID controllerId;

    public TezzeretMasterOfMetalControlEffect(UUID controllerId) {
        super(Duration.EndOfGame, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.controllerId = controllerId;
    }

    public TezzeretMasterOfMetalControlEffect(final TezzeretMasterOfMetalControlEffect effect) {
        super(effect);
        this.controllerId = effect.controllerId;
    }

    @Override
    public TezzeretMasterOfMetalControlEffect copy() {
        return new TezzeretMasterOfMetalControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null && controllerId != null) {
            return permanent.changeControllerId(controllerId, game);
        }
        return false;
    }
}
