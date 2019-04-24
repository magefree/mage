
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
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
 * @author LoneFox
 */
public final class KavuMauler extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("other attacking Kavu");

    static {
        filter.add(new SubtypePredicate(SubType.KAVU));
        filter.add(new AnotherPredicate());
    }

    public KavuMauler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.KAVU);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever Kavu Mauler attacks, it gets +1/+1 until end of turn for each other attacking Kavu.
        PermanentsOnBattlefieldCount value = new PermanentsOnBattlefieldCount(filter);
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(value, value, Duration.EndOfTurn, true), false));
    }

    public KavuMauler(final KavuMauler card) {
        super(card);
    }

    @Override
    public KavuMauler copy() {
        return new KavuMauler(this);
    }
}
