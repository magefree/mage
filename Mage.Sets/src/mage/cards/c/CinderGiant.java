
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author fireshoes
 */
public final class CinderGiant extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("other creature you control");
    static {
        filter.add(AnotherPredicate.instance);
    }

    public CinderGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, Cinder Giant deals 2 damage to each other creature you control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DamageAllEffect(2, filter), TargetController.YOU, false));
    }

    private CinderGiant(final CinderGiant card) {
        super(card);
    }

    @Override
    public CinderGiant copy() {
        return new CinderGiant(this);
    }
}
