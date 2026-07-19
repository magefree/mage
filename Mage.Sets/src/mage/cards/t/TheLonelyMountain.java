package mage.cards.t;

import java.util.UUID;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.Dwarf22Token;
import mage.util.CardUtil;
import mage.abilities.condition.Condition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.RedManaAbility;

/**
 *
 * @author muz
 */
public final class TheLonelyMountain extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("you control an Equipment");

    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public TheLonelyMountain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.MOUNTAIN);

        // ({T}: Add {R}.)
        this.addAbility(new RedManaAbility());

        // This land enters tapped unless you control an Equipment.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition));

        // {4}{R}, {T}: Create a 2/2 red Dwarf creature token. This ability costs {1} less to activate for each Equipment you control. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
            new CreateTokenEffect(new Dwarf22Token()),
            new ManaCostsImpl<>("{4}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new InfoEffect(
            "This ability costs {1} less to activate for each Equipment you control"
        ));
        ability.setCostAdjuster(TheLonelyMountainAdjuster.instance);
        ability.addHint(TheLonelyMountainAdjuster.getHint());
        this.addAbility(ability);
    }

    private TheLonelyMountain(final TheLonelyMountain card) {
        super(card);
    }

    @Override
    public TheLonelyMountain copy() {
        return new TheLonelyMountain(this);
    }
}

enum TheLonelyMountainAdjuster implements CostAdjuster {
    instance;

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.EQUIPMENT);
    private static final DynamicValue equipmentCount = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Equipment you control", equipmentCount);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public void reduceCost(Ability ability, Game game) {
        CardUtil.reduceCost(ability, equipmentCount.calculate(game, ability, null));
    }
}
