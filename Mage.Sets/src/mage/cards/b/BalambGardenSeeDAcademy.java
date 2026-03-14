package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BalambGardenSeeDAcademy extends TransformingDoubleFacedCard {

    public BalambGardenSeeDAcademy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{}, new CardType[]{CardType.LAND}, new SubType[]{SubType.TOWN}, "",
                "Balamb Garden, Airborne",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.VEHICLE}, "");
        this.getRightHalfCard().setPT(5, 4);

        // This land enters tapped.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // Add {G} or {U}.
        this.getLeftHalfCard().addAbility(new GreenManaAbility());
        this.getLeftHalfCard().addAbility(new BlueManaAbility());

        // {5}{G}{U}, {T}: Transform this land. This ability costs {1} less to activate for each other Town you control.
        Ability ability = new SimpleActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{5}{G}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new InfoEffect("This ability costs {1} less to activate for each other Town you control"));
        this.getLeftHalfCard().addAbility(ability
                .setCostAdjuster(BalambGardenSeeDAcademyAdjuster.instance)
                .addHint(BalambGardenSeeDAcademyAdjuster.getHint()));

        // Balamb Garden, Airborne
        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Whenever Balamb Garden attacks, draw a card.
        this.getRightHalfCard().addAbility(new AttacksTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Crew 1
        this.getRightHalfCard().addAbility(new CrewAbility(1));
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
