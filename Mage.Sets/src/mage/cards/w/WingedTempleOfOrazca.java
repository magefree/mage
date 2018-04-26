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
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class WingedTempleOfOrazca extends CardImpl {

    public WingedTempleOfOrazca(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.addSuperType(SuperType.LEGENDARY);

        this.nightCard = true;

        // <i>(Transforms from Hadana's Climb.)</i>
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new InfoEffect("<i>(Transforms from Hadana's Climb.)</i>"));
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {1}{G}{U}, {T}: Target creature you control gains flying and gets +X/+X until end of turn, where X is its power.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new WingedTempleOfOrazcaEffect(), new ManaCostsImpl<>("{1}{G}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    public WingedTempleOfOrazca(final WingedTempleOfOrazca card) {
        super(card);
    }

    @Override
    public WingedTempleOfOrazca copy() {
        return new WingedTempleOfOrazca(this);
    }
}

class WingedTempleOfOrazcaEffect extends OneShotEffect {

    public WingedTempleOfOrazcaEffect() {
        super(Outcome.Benefit);
        this.staticText = "it gains flying and gets +X/+X until end of turn, where X is its power";
    }

    public WingedTempleOfOrazcaEffect(final WingedTempleOfOrazcaEffect effect) {
        super(effect);
    }

    @Override
    public WingedTempleOfOrazcaEffect copy() {
        return new WingedTempleOfOrazcaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));
        if (creature != null && creature.isCreature()) {
            int pow = creature.getPower().getValue();
            ContinuousEffect effect = new BoostTargetEffect(pow, pow, Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(creature, game));
            game.addEffect(effect, source);
            effect = new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(creature, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}
