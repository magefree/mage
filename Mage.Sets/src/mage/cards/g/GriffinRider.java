package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author nantuko
 */
public final class GriffinRider extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(new FilterControlledCreaturePermanent(SubType.GRIFFIN));

    public GriffinRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // As long as you control a Griffin creature, Griffin Rider gets +3/+3 and has flying.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(new BoostSourceEffect(3, 3, Duration.WhileOnBattlefield),
                condition, "as long as you control a Griffin creature, {this} gets +3/+3"));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance()),
                condition, "and has flying"));
        this.addAbility(ability);
    }

    private GriffinRider(final GriffinRider card) {
        super(card);
    }

    @Override
    public GriffinRider copy() {
        return new GriffinRider(this);
    }
}
