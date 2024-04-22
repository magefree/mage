package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class HyldasCrownOfWinter extends CardImpl {

    private static final FilterCreaturePermanent filter =
            new FilterCreaturePermanent("tapped creature your opponents control");

    static {
        filter.add(TappedPredicate.TAPPED);
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Tapped creatures your opponents control", xValue);

    public HyldasCrownOfWinter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        
        this.supertype.add(SuperType.LEGENDARY);

        // {1}, {T}: Tap target creature. This ability cost {1} less to activate during your turn.
        Ability ability = new SimpleActivatedAbility(
                new TapTargetEffect(), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new InfoEffect("this ability costs {1} less to activate during your turn"));
        ability.setCostAdjuster(HyldasCrownOfWinterAdjuster.instance);
        this.addAbility(ability);

        // {3}, Sacrifice Hylda's Crown of Winter: Draw a card for each tapped creature your opponents control.
        ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(xValue),
                new GenericManaCost(3)
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addHint(hint);
        this.addAbility(ability);
    }

    private HyldasCrownOfWinter(final HyldasCrownOfWinter card) {
        super(card);
    }

    @Override
    public HyldasCrownOfWinter copy() {
        return new HyldasCrownOfWinter(this);
    }
}

enum HyldasCrownOfWinterAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (ability.getControllerId().equals(game.getActivePlayerId())) {
            CardUtil.reduceCost(ability, 1);
        }
    }
}
