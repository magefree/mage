
package mage.cards.k;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

/**
 *
 * @author LevelX2
 */
public final class KavuLair extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }
    
    public KavuLair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");

        // Whenever a creature with power 4 or greater enters the battlefield, its controller draws a card.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD,
                new DrawCardTargetEffect(1).setText("its controller draws a card"),
                filter, false, SetTargetPointer.PLAYER
        ));
    }

    private KavuLair(final KavuLair card) {
        super(card);
    }

    @Override
    public KavuLair copy() {
        return new KavuLair(this);
    }
}
