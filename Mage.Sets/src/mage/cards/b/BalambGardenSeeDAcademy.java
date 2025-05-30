package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BalambGardenSeeDAcademy extends CardImpl {

    public BalambGardenSeeDAcademy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.TOWN);
        this.secondSideCardClazz = mage.cards.b.BalambGardenAirborne.class;

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Add {G} or {U}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());

        // {5}{G}{U}, {T}: Transform this land. This ability costs {1} less to activate for each other Town you control.
        this.addAbility(new TransformAbility());
        Ability ability = new SimpleActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{5}{G}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new InfoEffect("This ability costs {1} less to activate for each other Town you control"));
        this.addAbility(ability
                .setCostAdjuster(BalambGardenSeeDAcademyAdjuster.instance)
                .addHint(BalambGardenSeeDAcademyAdjuster.getHint()));
    }

    private BalambGardenSeeDAcademy(final BalambGardenSeeDAcademy card) {
        super(card);
    }

    @Override
    public BalambGardenSeeDAcademy copy() {
        return new BalambGardenSeeDAcademy(this);
    }
}

enum BalambGardenSeeDAcademyAdjuster implements CostAdjuster {
    instance;
    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.TOWN);

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Other Towns you control", xValue);

    static Hint getHint() {
        return hint;
    }

    @Override
    public void reduceCost(Ability ability, Game game) {
        int count = xValue.calculate(game, ability, null);
        CardUtil.reduceCost(ability, count);
    }
}
