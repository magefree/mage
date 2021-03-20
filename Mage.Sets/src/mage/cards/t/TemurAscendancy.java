
package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

/**
 *
 * @author LevelX2
 */
public final class TemurAscendancy extends CardImpl {

    final private static FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature with power 4 or greater");
    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public TemurAscendancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}{U}{R}");


        // Creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield, new FilterControlledCreaturePermanent("Creatures"))));
        
        // Whenever a creature with power 4 or greater enters the battlefield under your control, you may draw a card.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), filter, true));
    }

    private TemurAscendancy(final TemurAscendancy card) {
        super(card);
    }

    @Override
    public TemurAscendancy copy() {
        return new TemurAscendancy(this);
    }
}
