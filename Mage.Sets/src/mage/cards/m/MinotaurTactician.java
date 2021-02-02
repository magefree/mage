
package mage.cards.m;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class MinotaurTactician extends CardImpl {

    private static final FilterControlledCreaturePermanent filterWhite = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterBlue = new FilterControlledCreaturePermanent();

    static {
        filterWhite.add(new ColorPredicate(ObjectColor.WHITE));
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
    }

    static final private String ruleWhite = "{this} gets +1/+1 as long as you control a white creature";

    static final private String ruleBlue = "{this} gets +1/+1 as long as you control a blue creature";

    public MinotaurTactician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Minotaur Tactician gets +1/+1 as long as you control a white creature.
        Condition conditionWhite = new PermanentsOnTheBattlefieldCondition(filterWhite);
        Effect effectWhite = new ConditionalContinuousEffect(new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield), conditionWhite, ruleWhite);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effectWhite));

        // Minotaur Tactician gets +1/+1 as long as you control a blue creature.
        Condition conditionBlue = new PermanentsOnTheBattlefieldCondition(filterBlue);
        Effect effectBlue = new ConditionalContinuousEffect(new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield), conditionBlue, ruleBlue);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effectBlue));
    }

    private MinotaurTactician(final MinotaurTactician card) {
        super(card);
    }

    @Override
    public MinotaurTactician copy() {
        return new MinotaurTactician(this);
    }
}
