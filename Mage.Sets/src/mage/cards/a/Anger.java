
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Backfir3
 */
public final class Anger extends CardImpl {

    private static final String ruleText = "As long as Anger is in your graveyard and you control a Mountain, creatures you control have haste";

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Mountain");

    static {
        filter.add(CardType.LAND.getPredicate());
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public Anger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.INCARNATION);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // As long as Anger is in your graveyard and you control a Mountain, creatures you control have haste
        ContinuousEffect effect = new GainAbilityControlledEffect(HasteAbility.getInstance(),
                Duration.WhileOnBattlefield, new FilterCreaturePermanent());
        ConditionalContinuousEffect angerEffect = new ConditionalContinuousEffect(effect,
                new PermanentsOnTheBattlefieldCondition(filter), ruleText);
        this.addAbility(new SimpleStaticAbility(Zone.GRAVEYARD, angerEffect));
    }

    private Anger(final Anger card) {
        super(card);
    }

    @Override
    public Anger copy() {
        return new Anger(this);
    }
}
