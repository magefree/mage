package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MagitekInfantry extends CardImpl {

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_CONTROLLED_ANOTHER_ARTIFACT);
    private static final Hint hint = new ConditionHint(condition, "You control another artifact");
    private static final FilterCard filter = new FilterCard("card named Magitek Infantry");

    static {
        filter.add(new NamePredicate("Magitek Infantry"));
    }

    public MagitekInfantry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // This creature gets +1/+0 as long as you control another artifact.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield),
                condition, "{this} gets +1/+0 as long as you control another artifact"
        )).addHint(hint));

        // {2}{W}: Search your library for a card named Magitek Infantry, put it onto the battlefield tapped, then shuffle.
        this.addAbility(new SimpleActivatedAbility(
                new SearchLibraryPutInPlayEffect(
                        new TargetCardInLibrary(filter), true
                ), new ManaCostsImpl<>("{2}{W}")
        ));
    }

    private MagitekInfantry(final MagitekInfantry card) {
        super(card);
    }

    @Override
    public MagitekInfantry copy() {
        return new MagitekInfantry(this);
    }
}
