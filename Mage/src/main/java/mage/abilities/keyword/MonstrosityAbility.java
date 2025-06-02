package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.common.MonstrousHint;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.Optional;


/**
 * Monstrosity
 * <p>
 * 701.28. Monstrosity
 * <p>
 * 701.28a “Monstrosity N” means “If this permanent isn't monstrous, put N +1/+1 counters on it
 * and it becomes monstrous.” Monstrous is a condition of that permanent that can be
 * referred to by other abilities.
 * <p>
 * 701.28b If a permanent's ability instructs a player to “monstrosity X,” other abilities of
 * that permanent may also refer to X. The value of X in those abilities is equal to
 * the value of X as that permanent became monstrous.
 * <p>
 * * Once a creature becomes monstrous, it can't become monstrous again. If the creature
 * is already monstrous when the monstrosity ability resolves, nothing happens.
 * <p>
 * * Monstrous isn't an ability that a creature has. It's just something true about that
 * creature. If the creature stops being a creature or loses its abilities, it will
 * continue to be monstrous.
 * <p>
 * * An ability that triggers when a creature becomes monstrous won't trigger if that creature
 * isn't on the battlefield when its monstrosity ability resolves.
 *
 * @author LevelX2
 */

public class MonstrosityAbility extends ActivatedAbilityImpl {

    private final DynamicValue monstrosityValue;

    public MonstrosityAbility(String manaString, int monstrosityValue) {
        this(manaString, monstrosityValue, null, "");
    }

    /**
     * @param manaString
     * @param monstrosityValue use Integer.MAX_VALUE for monstrosity X.
     * @param costAdjuster
     * @param costAdjusterText Clarifies the cost adjusting condition(s).
     */
    public MonstrosityAbility(String manaString, int monstrosityValue, CostAdjuster costAdjuster, String costAdjusterText) {
        this(manaString, StaticValue.get(monstrosityValue), costAdjuster, costAdjusterText);
    }

    public MonstrosityAbility(String manaString, DynamicValue monstrosityValue, CostAdjuster costAdjuster, String costAdjusterText) {
        super(Zone.BATTLEFIELD, new BecomeMonstrousSourceEffect(monstrosityValue, costAdjusterText), new ManaCostsImpl<>(manaString));
        this.monstrosityValue = monstrosityValue;
        this.addHint(MonstrousHint.instance);
        setCostAdjuster(costAdjuster);
    }

    protected MonstrosityAbility(final MonstrosityAbility ability) {
        super(ability);
        this.monstrosityValue = ability.monstrosityValue;
    }

    @Override
    public MonstrosityAbility copy() {
        return new MonstrosityAbility(this);
    }

    public DynamicValue getMonstrosityValue() {
        return monstrosityValue;
    }
}


class BecomeMonstrousSourceEffect extends OneShotEffect {

    BecomeMonstrousSourceEffect(DynamicValue monstrosityValue) {
        this(monstrosityValue, "");
    }

    BecomeMonstrousSourceEffect(DynamicValue monstrosityValue, String costAdjusterText) {
        super(Outcome.BoostCreature);
        this.staticText = setText(monstrosityValue, costAdjusterText);
    }

    protected BecomeMonstrousSourceEffect(final BecomeMonstrousSourceEffect effect) {
        super(effect);
    }

    @Override
    public BecomeMonstrousSourceEffect copy() {
        return new BecomeMonstrousSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || permanent.isMonstrous()) {
            return false;
        }
        int monstrosityValue = Optional
                .ofNullable(source)
                .map(MonstrosityAbility.class::cast)
                .map(MonstrosityAbility::getMonstrosityValue)
                .map(dynamicValue -> dynamicValue.calculate(game, source, this))
                // handle monstrosity = X
                .map(i -> i == Integer.MAX_VALUE ? CardUtil.getSourceCostsTag(game, source, "X", 0) : i)
                .orElse(0);
        permanent.addCounters(
                CounterType.P1P1.createInstance(monstrosityValue),
                source.getControllerId(), source, game
        );
        permanent.setMonstrous(true);
        game.fireEvent(GameEvent.getEvent(
                GameEvent.EventType.BECOMES_MONSTROUS, source.getSourceId(),
                source, source.getControllerId(), monstrosityValue
        ));
        return true;
    }

    private String setText(DynamicValue monstrosityValue, String costAdjusterText) {
        if (!(monstrosityValue instanceof StaticValue)) {
            return "Monstrosity X, where X is " + monstrosityValue.getMessage() + ". " + costAdjusterText +
                    "<i>(If this creature isn't monstrous, put X +1/+1 counters on it and it becomes monstrous.)</i>";
        }
        int value = ((StaticValue) monstrosityValue).getValue();
        return "Monstrosity " + (value == Integer.MAX_VALUE ? "X" : value) +
                ". " + costAdjusterText + "<i>(If this creature isn't monstrous, put " +
                (value == Integer.MAX_VALUE ? "X" : CardUtil.numberToText(value)) +
                " +1/+1 counters on it and it becomes monstrous.)</i>";
    }
}
