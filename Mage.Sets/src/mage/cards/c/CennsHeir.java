package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
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
public final class CennsHeir extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("other attacking Kithkin");

    static {
        filter.add(SubType.KITHKIN.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public CennsHeir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Cenn's Heir attacks, it gets +1/+1 until end of turn for each other attacking Kithkin.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(xValue, xValue, Duration.EndOfTurn, true, "it"), false));
    }

    private CennsHeir(final CennsHeir card) {
        super(card);
    }

    @Override
    public CennsHeir copy() {
        return new CennsHeir(this);
    }
}
