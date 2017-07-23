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
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.BoostAllOfChosenSubtypeEffect;
import mage.abilities.effects.common.enterAttribute.EnterAttributeAddChosenSubtypeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author nantuko
 */
public class AdaptiveAutomaton extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control of the chosen type");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public AdaptiveAutomaton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As Adaptive Automaton enters the battlefield, choose a creature type.
        // Adaptive Automaton is the chosen type in addition to its other types.
        AsEntersBattlefieldAbility ability = new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature), null, EnterEventType.SELF);
        ability.addEffect(new EnterAttributeAddChosenSubtypeEffect());
        this.addAbility(ability);

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AdaptiveAutomatonAddSubtypeEffect()));
        // Other creatures you control of the chosen type get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllOfChosenSubtypeEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));
    }

    public AdaptiveAutomaton(final AdaptiveAutomaton card) {
        super(card);
    }

    @Override
    public AdaptiveAutomaton copy() {
        return new AdaptiveAutomaton(this);
    }
}

class AdaptiveAutomatonAddSubtypeEffect extends ContinuousEffectImpl {

    public AdaptiveAutomatonAddSubtypeEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "{this} is the chosen type in addition to its other types";
    }

    public AdaptiveAutomatonAddSubtypeEffect(final AdaptiveAutomatonAddSubtypeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            String subtype = (String) game.getState().getValue(permanent.getId() + "_type");
            if (subtype != null && !permanent.getSubtype(game).contains(subtype)) {
                permanent.getSubtype(game).add(subtype);
            }
        }
        return true;
    }

    @Override
    public AdaptiveAutomatonAddSubtypeEffect copy() {
        return new AdaptiveAutomatonAddSubtypeEffect(this);
    }
}
