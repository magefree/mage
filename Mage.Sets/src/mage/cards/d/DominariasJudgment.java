
package mage.cards.d;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;

/**
 * 
 * @author L_J
 */
public final class DominariasJudgment extends CardImpl {

    private static final FilterLandPermanent filterPlains = new FilterLandPermanent("Plains");
    private static final FilterLandPermanent filterIsland = new FilterLandPermanent("Island");
    private static final FilterLandPermanent filterSwamp = new FilterLandPermanent("Swamp");
    private static final FilterLandPermanent filterMountain = new FilterLandPermanent("Mountain");
    private static final FilterLandPermanent filterForest = new FilterLandPermanent("Forest");

    static {
        filterPlains.add(SubType.PLAINS.getPredicate());
        filterIsland.add(SubType.ISLAND.getPredicate());
        filterSwamp.add(SubType.SWAMP.getPredicate());
        filterMountain.add(SubType.MOUNTAIN.getPredicate());
        filterForest.add(SubType.FOREST.getPredicate());
    }

    public DominariasJudgment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Until end of turn, creatures you control gain protection from white if you control a Plains, from blue if you control an Island, from black if you control a Swamp, from red if you control a Mountain, and from green if you control a Forest.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(new GainAbilityControlledEffect(ProtectionAbility.from(ObjectColor.WHITE), Duration.EndOfTurn, 
                StaticFilters.FILTER_PERMANENT_CREATURE, false), new PermanentsOnTheBattlefieldCondition(filterPlains),"Until end of turn, creatures you control gain protection from white if you control a Plains,"));
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(new GainAbilityControlledEffect(ProtectionAbility.from(ObjectColor.BLUE), Duration.EndOfTurn, 
                StaticFilters.FILTER_PERMANENT_CREATURE, false), new PermanentsOnTheBattlefieldCondition(filterIsland)," from blue if you control an Island,"));
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(new GainAbilityControlledEffect(ProtectionAbility.from(ObjectColor.BLACK), Duration.EndOfTurn, 
                StaticFilters.FILTER_PERMANENT_CREATURE, false), new PermanentsOnTheBattlefieldCondition(filterSwamp)," from black if you control a Swamp,"));
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(new GainAbilityControlledEffect(ProtectionAbility.from(ObjectColor.RED), Duration.EndOfTurn, 
                StaticFilters.FILTER_PERMANENT_CREATURE, false), new PermanentsOnTheBattlefieldCondition(filterMountain)," from red if you control a Mountain,"));
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(new GainAbilityControlledEffect(ProtectionAbility.from(ObjectColor.GREEN), Duration.EndOfTurn, 
                StaticFilters.FILTER_PERMANENT_CREATURE, false), new PermanentsOnTheBattlefieldCondition(filterForest)," and from green if you control a Forest"));
    }

    private DominariasJudgment(final DominariasJudgment card) {
        super(card);
    }

    @Override
    public DominariasJudgment copy() {
        return new DominariasJudgment(this);
    }
}
