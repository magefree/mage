package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.CastOnlyIfConditionIsTrueAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author TheElk801
 */
public final class Blizzard extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("if you control a snow land");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creatures with flying");

    static {
        filter.add(SuperType.SNOW.getPredicate());
        filter2.add(new AbilityPredicate(FlyingAbility.class));
    }

    public Blizzard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}{G}");

        // Cast Blizzard only if you control a snow land.
        this.addAbility(new CastOnlyIfConditionIsTrueAbility(
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 0)
        ));

        // Cumulative upkeep {2}
        this.addAbility(new CumulativeUpkeepAbility(new GenericManaCost(2)));

        // Creatures with flying don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new DontUntapInControllersUntapStepAllEffect(
                        Duration.WhileOnBattlefield,
                        TargetController.ANY,
                        filter2
                )
        ));
    }

    private Blizzard(final Blizzard card) {
        super(card);
    }

    @Override
    public Blizzard copy() {
        return new Blizzard(this);
    }
}
