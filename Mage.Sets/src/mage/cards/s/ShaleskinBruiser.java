
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;

/**
 *
 * @author Eirkei
 */
public final class ShaleskinBruiser extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("other attacking Beast");
    
    static {
        filter.add(new SubtypePredicate(SubType.BEAST));
        filter.add(new AnotherPredicate());
    }

    
    public ShaleskinBruiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{R}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever Shaleskin Bruiser attacks, it gets +3/+0 until end of turn for each other attacking Beast.
        PermanentsOnBattlefieldCount value = new PermanentsOnBattlefieldCount(filter, 3);
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(value, new StaticValue(0), Duration.EndOfTurn, true), false));
    }

    public ShaleskinBruiser(final ShaleskinBruiser card) {
        super(card);
    }

    @Override
    public ShaleskinBruiser copy() {
        return new ShaleskinBruiser(this);
    }
}
