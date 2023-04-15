package mage.cards.h;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.ValueHint;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.PermanentsEnteredBattlefieldWatcher;

/**
 *
 * @author weirddan455
 */
public final class HobgoblinBanditLord extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.GOBLIN, "Goblins");

    public HobgoblinBanditLord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Other Goblins you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // {R}, {T}: Hobgoblin Bandit Lord deals damage equal to the number of Goblins that entered the battlefield under your control this turn to any target.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(GoblinsEnteredThisTurnDynamicValue.instance)
                    .setText("{this} deals damage equal to the number of Goblins that entered the battlefield under your control this turn to any target"),
                new ManaCostsImpl<>("{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        ability.addHint(new ValueHint("Goblins that entered the battlefield under your control this turn", GoblinsEnteredThisTurnDynamicValue.instance));
        this.addAbility(ability, new PermanentsEnteredBattlefieldWatcher());
    }

    private HobgoblinBanditLord(final HobgoblinBanditLord card) {
        super(card);
    }

    @Override
    public HobgoblinBanditLord copy() {
        return new HobgoblinBanditLord(this);
    }
}

enum GoblinsEnteredThisTurnDynamicValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int goblins = 0;
        PermanentsEnteredBattlefieldWatcher watcher = game.getState().getWatcher(PermanentsEnteredBattlefieldWatcher.class);
        if (watcher != null) {
            List<Permanent> permanents = watcher.getThisTurnEnteringPermanents(sourceAbility.getControllerId());
            if (permanents != null) {
                for (Permanent permanent : permanents) {
                    if (permanent.hasSubtype(SubType.GOBLIN, game)) {
                        goblins++;
                    }
                }
            }
        }
        return goblins;
    }

    @Override
    public GoblinsEnteredThisTurnDynamicValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "Goblins that entered the battlefield under your control this turn";
    }
}
