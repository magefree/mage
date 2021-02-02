
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class GuardianOfTheGreatConduit extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();
    private static final String rule = "As long as you control a Nissa planeswalker, {this} gets +2/+0";

    static {
        filter.add(CardType.PLANESWALKER.getPredicate());
        filter.add(SubType.NISSA.getPredicate());
    }

    public GuardianOfTheGreatConduit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());
        // As long as you control a Nissa planeswalker, Guardian of the Great Conduit gets +2/+0 and has vigilance.
        ConditionalContinuousEffect effect1 = new ConditionalContinuousEffect(new BoostSourceEffect(2, 0, Duration.WhileOnBattlefield), new PermanentsOnTheBattlefieldCondition(filter), rule);
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect1);
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield), new PermanentsOnTheBattlefieldCondition(filter), "and has vigilance"));
        this.addAbility(ability);
    }

    private GuardianOfTheGreatConduit(final GuardianOfTheGreatConduit card) {
        super(card);
    }

    @Override
    public GuardianOfTheGreatConduit copy() {
        return new GuardianOfTheGreatConduit(this);
    }
}
