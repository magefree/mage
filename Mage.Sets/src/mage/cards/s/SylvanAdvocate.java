
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author fireshoes
 */
public final class SylvanAdvocate extends CardImpl {

    private static final String rule1 = "As long as you control six or more lands, {this}";
    private static final String rule2 = "and land creatures you control get +2/+2.";
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("land creatures");

    static {
        filter.add(CardType.LAND.getPredicate());
    }

    public SylvanAdvocate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // As long as you control six or more lands, Sylvan Advocate and land creatures you control get +2/+2.
        ConditionalContinuousEffect effect1 = new ConditionalContinuousEffect(new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield),
                new PermanentsOnTheBattlefieldCondition(new FilterControlledLandPermanent(), ComparisonType.MORE_THAN, 5), rule1);
        ConditionalContinuousEffect effect2 = new ConditionalContinuousEffect(new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield, filter, true),
                new PermanentsOnTheBattlefieldCondition(new FilterControlledLandPermanent(), ComparisonType.MORE_THAN, 5), rule2);
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect1);
        ability.addEffect(effect2);
        this.addAbility(ability);
    }

    private SylvanAdvocate(final SylvanAdvocate card) {
        super(card);
    }

    @Override
    public SylvanAdvocate copy() {
        return new SylvanAdvocate(this);
    }
}
