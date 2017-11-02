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
package mage.cards.s;

import java.util.List;
import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.SubTypeList;

/**
 *
 * @author TheElk801
 */
public class ShadesBreath extends CardImpl {

    public ShadesBreath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Until end of turn, each creature you control becomes a black Shade and gains "{B}: This creature gets +1/+1 until end of turn."
        this.getSpellAbility().addEffect(new ShadesBreathSetColorEffect());
        this.getSpellAbility().addEffect(new ShadesBreathSetSubtypeEffect());
        this.getSpellAbility().addEffect(
                new GainAbilityControlledEffect(new SimpleActivatedAbility(
                        Zone.BATTLEFIELD,
                        new BoostSourceEffect(1, 1, Duration.EndOfTurn).setText("this creature gets +1/+1 until end of turn"),
                        new ManaCostsImpl("{B}")
                ), Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_A_CREATURE)
                        .setText("and gains \"{B}: This creature gets +1/+1 until end of turn.\"")
        );
    }

    public ShadesBreath(final ShadesBreath card) {
        super(card);
    }

    @Override
    public ShadesBreath copy() {
        return new ShadesBreath(this);
    }
}

class ShadesBreathSetColorEffect extends ContinuousEffectImpl {

    public ShadesBreathSetColorEffect() {
        super(Duration.EndOfTurn, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Benefit);
        staticText = "Until end of turn, each creature you control becomes a black";
    }

    public ShadesBreathSetColorEffect(final ShadesBreathSetColorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            if (permanent != null) {
                permanent.getColor(game).setColor(ObjectColor.BLACK);
            }
        }
        return true;
    }

    @Override
    public ShadesBreathSetColorEffect copy() {
        return new ShadesBreathSetColorEffect(this);
    }
}

class ShadesBreathSetSubtypeEffect extends ContinuousEffectImpl {

    public ShadesBreathSetSubtypeEffect() {
        super(Duration.EndOfTurn, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "Shade";
    }

    public ShadesBreathSetSubtypeEffect(final ShadesBreathSetSubtypeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            if (permanent != null) {
                SubTypeList subtype = permanent.getSubtype(game);
                if (subtype != null && subtype.size() != 1 || !subtype.contains(SubType.SHADE)) {
                    subtype.removeAll(SubType.getCreatureTypes(false));
                    subtype.add(SubType.SHADE);
                }
            }
        }
        return true;
    }

    @Override
    public ShadesBreathSetSubtypeEffect copy() {
        return new ShadesBreathSetSubtypeEffect(this);
    }
}
