
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;

/**
 *
 * @author LevelX2
 */
public final class SquirrelMob extends CardImpl {

    public SquirrelMob(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.SQUIRREL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Squirrel Mob gets +1/+1 for each other Squirrel on the battlefield.
        FilterCreaturePermanent filter = new FilterCreaturePermanent("other Squirrel");
        filter.add(SubType.SQUIRREL.getPredicate());
        filter.add(Predicates.not(new PermanentIdPredicate(this.getId())));
        DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
        Effect effect = new BoostSourceEffect(xValue, xValue, Duration.WhileOnBattlefield, false);
        effect.setText("{this} gets +1/+1 for each other Squirrel on the battlefield");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private SquirrelMob(final SquirrelMob card) {
        super(card);
    }

    @Override
    public SquirrelMob copy() {
        return new SquirrelMob(this);
    }
}
