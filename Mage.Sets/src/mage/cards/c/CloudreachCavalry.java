
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
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
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author LoneFox
 */
public final class CloudreachCavalry extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Bird");

    static {
        filter.add(SubType.BIRD.getPredicate());
    }

    public CloudreachCavalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // As long as you control a Bird, Cloudreach Cavalry gets +2/+2 and has flying.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield),
            new PermanentsOnTheBattlefieldCondition(filter),
            "As long as you control a Bird, {this} gets +2/+2"));
        ability.addEffect(new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(FlyingAbility.getInstance()),
            new PermanentsOnTheBattlefieldCondition(filter),
            "and has flying"));
        this.addAbility(ability);
    }

    private CloudreachCavalry(final CloudreachCavalry card) {
        super(card);
    }

    @Override
    public CloudreachCavalry copy() {
        return new CloudreachCavalry(this);
    }
}
