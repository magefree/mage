package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author LoneFox
 */
public final class KavuMauler extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("other attacking Kavu");

    static {
        filter.add(SubType.KAVU.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public KavuMauler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.KAVU);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever Kavu Mauler attacks, it gets +1/+1 until end of turn for each other attacking Kavu.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(xValue, xValue, Duration.EndOfTurn, true, "it"), false));
    }

    private KavuMauler(final KavuMauler card) {
        super(card);
    }

    @Override
    public KavuMauler copy() {
        return new KavuMauler(this);
    }
}
