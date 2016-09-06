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
package mage.sets.magic2011;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CaptivatingVampire extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("Vampire creatures");
    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent("untapped Vampires you control");

    static {
        filter1.add(new SubtypePredicate("Vampire"));
        filter2.add(new SubtypePredicate("Vampire"));
        filter2.add(Predicates.not(new TappedPredicate()));
    }

    public CaptivatingVampire(UUID ownerId) {
        super(ownerId, 87, "Captivating Vampire", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.expansionSetCode = "M11";

        this.subtype.add("Vampire");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Vampire creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter1, true)));

        // Tap five untapped Vampires you control: Gain control of target creature. It becomes a Vampire in addition to its other types.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CaptivatingVampireEffect(), new TapTargetCost(new TargetControlledCreaturePermanent(5, 5, filter2, true)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public CaptivatingVampire(final CaptivatingVampire card) {
        super(card);
    }

    @Override
    public CaptivatingVampire copy() {
        return new CaptivatingVampire(this);
    }

}

class CaptivatingVampireEffect extends ContinuousEffectImpl {

    public CaptivatingVampireEffect() {
        super(Duration.Custom, Outcome.Detriment);
        staticText = "Gain control of target creature. It becomes a Vampire in addition to its other types";
    }

    public CaptivatingVampireEffect(final CaptivatingVampireEffect effect) {
        super(effect);
    }

    @Override
    public CaptivatingVampireEffect copy() {
        return new CaptivatingVampireEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            switch (layer) {
                case ControlChangingEffects_2:
                    if (sublayer == SubLayer.NA) {
                        permanent.changeControllerId(source.getControllerId(), game);
                    }
                    break;
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        if (!permanent.getSubtype(game).contains("Vampire")) {
                            permanent.getSubtype(game).add("Vampire");
                        }
                    }
                    break;
            }
            return true;
        }
        discard();
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.ControlChangingEffects_2 || layer == Layer.TypeChangingEffects_4;
    }

}
