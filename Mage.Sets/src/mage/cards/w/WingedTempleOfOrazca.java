
package mage.cards.w;

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
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class WingedTempleOfOrazca extends CardImpl {

    public WingedTempleOfOrazca(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);

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

    private WingedTempleOfOrazca(final WingedTempleOfOrazca card) {
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
        this.staticText = "target creature you control gains flying and gets +X/+X until end of turn, where X is its power";
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
        if (creature != null && creature.isCreature(game)) {
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
