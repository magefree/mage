package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.TargetHasCounterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HadanasClimb extends TransformingDoubleFacedCard {

    public HadanasClimb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "{1}{G}{U}",
                "Winged Temple of Orazca",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // Hadana's Climb
        // At the beginning of combat on your turn, put a +1/+1 counter on target creature you control. Then if that creature has three or more +1/+1 counters on it, transform Hadana's Climb.
        Ability ability = new BeginningOfCombatTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addEffect(new ConditionalOneShotEffect(new TransformSourceEffect(), new TargetHasCounterCondition(CounterType.P1P1, 3, Integer.MAX_VALUE),
                "Then if that creature has three or more +1/+1 counters on it, transform {this}"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.getLeftHalfCard().addAbility(ability);

        // Winged Temple of Orazca
        // {T}: Add one mana of any color.
        this.getRightHalfCard().addAbility(new AnyColorManaAbility());

        // {1}{G}{U}, {T}: Target creature you control gains flying and gets +X/+X until end of turn, where X is its power.
        Ability backAbility = new SimpleActivatedAbility(new WingedTempleOfOrazcaEffect(), new ManaCostsImpl<>("{1}{G}{U}"));
        backAbility.addCost(new TapSourceCost());
        backAbility.addTarget(new TargetControlledCreaturePermanent());
        this.getRightHalfCard().addAbility(backAbility);
    }

    private HadanasClimb(final HadanasClimb card) {
        super(card);
    }

    @Override
    public HadanasClimb copy() {
        return new HadanasClimb(this);
    }
}

class WingedTempleOfOrazcaEffect extends OneShotEffect {

    WingedTempleOfOrazcaEffect() {
        super(Outcome.Benefit);
        this.staticText = "target creature you control gains flying and gets +X/+X until end of turn, where X is its power";
    }

    private WingedTempleOfOrazcaEffect(final WingedTempleOfOrazcaEffect effect) {
        super(effect);
    }

    @Override
    public WingedTempleOfOrazcaEffect copy() {
        return new WingedTempleOfOrazcaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
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
