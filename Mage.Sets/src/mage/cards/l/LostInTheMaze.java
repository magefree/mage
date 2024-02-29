package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LostInTheMaze extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("tapped creatures");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public LostInTheMaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{X}{U}{U}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Lost in the Maze enters the battlefield, tap X target creatures. Put a stun counter on each of those creatures you don't control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect("tap X target creatures"));
        ability.addEffect(new LostInTheMazeEffect());
        ability.setTargetAdjuster(LostInTheMazeAdjuster.instance);
        this.addAbility(ability);

        // Tapped creatures you control have hexproof.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HexproofAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));
    }

    private LostInTheMaze(final LostInTheMaze card) {
        super(card);
    }

    @Override
    public LostInTheMaze copy() {
        return new LostInTheMaze(this);
    }
}

enum LostInTheMazeAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetCreaturePermanent(ManacostVariableValue.ETB.calculate(game, ability, null)));
    }
}

class LostInTheMazeEffect extends OneShotEffect {

    LostInTheMazeEffect() {
        super(Outcome.Benefit);
        staticText = "Put a stun counter on each of those creatures you don't control";
    }

    private LostInTheMazeEffect(final LostInTheMazeEffect effect) {
        super(effect);
    }

    @Override
    public LostInTheMazeEffect copy() {
        return new LostInTheMazeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null && !permanent.isControlledBy(source.getControllerId())) {
                permanent.addCounters(CounterType.STUN.createInstance(), source, game);
            }
        }
        return true;
    }
}
