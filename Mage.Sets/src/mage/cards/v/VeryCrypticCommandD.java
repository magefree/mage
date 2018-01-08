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
package mage.cards.v;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseNewTargetsTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect.FaceDownType;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterStackObject;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NumberOfTargetsPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.TargetStackObject;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author spjspj
 */
public class VeryCrypticCommandD extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("spell or ability with a single target");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("nontoken creature");

    static {
        filter.add(new NumberOfTargetsPredicate(1));
        filter2.add(Predicates.not(new TokenPredicate()));
    }

    public VeryCrypticCommandD(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}{U}");

        // Choose two - 
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // Return target permanent to its controller�s hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());

        // Draw two cards, then discard a card.
        Mode mode = new Mode();
        mode.getEffects().add(new DrawCardSourceControllerEffect(2));
        Effect effect = new DiscardControllerEffect(1);
        effect.setText(", then discard a card");
        mode.getEffects().add(effect);
        this.getSpellAbility().getModes().addMode(mode);

        // Change the target of target spell with a single target.
        mode = new Mode();
        mode.getEffects().add(new ChooseNewTargetsTargetEffect(true, true));
        mode.getTargets().add(new TargetStackObject(filter));
        this.getSpellAbility().getModes().addMode(mode);

        // Turn over target nontoken creature.
        mode = new Mode();
        mode.getEffects().add(new TurnOverEffect());
        mode.getTargets().add(new TargetCreaturePermanent(filter2));
        this.getSpellAbility().getModes().addMode(mode);
    }

    public VeryCrypticCommandD(final VeryCrypticCommandD card) {
        super(card);
    }

    @Override
    public VeryCrypticCommandD copy() {
        return new VeryCrypticCommandD(this);
    }
}

class TurnOverEffect extends OneShotEffect {

    TurnOverEffect() {
        super(Outcome.Benefit);
        this.staticText = "Turn over target nontoken creature";
    }

    TurnOverEffect(final TurnOverEffect effect) {
        super(effect);
    }

    @Override
    public TurnOverEffect copy() {
        return new TurnOverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature != null) {
            if (creature.isFaceDown(game)) {
                creature.turnFaceUp(game, source.getControllerId());
            } else {
                creature.turnFaceDown(game, source.getControllerId());
                MageObjectReference objectReference = new MageObjectReference(creature.getId(), creature.getZoneChangeCounter(game), game);
                game.addEffect(new BecomesFaceDownCreatureEffect(null, objectReference, Duration.Custom, FaceDownType.MANUAL), source);
            }
            return true;
        }
        return false;
    }
}
