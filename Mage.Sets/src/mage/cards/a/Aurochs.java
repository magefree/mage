package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
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
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author anonymous
 */
public final class Aurochs extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("other attacking Aurochs");

    static {
        filter.add(SubType.AUROCHS.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public Aurochs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.AUROCHS);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever Aurochs attacks, it gets +1/+0 until end of turn for each other attacking Aurochs.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(xValue, StaticValue.get(0), Duration.EndOfTurn, true, "it"), false));
    }

    private Aurochs(final Aurochs card) {
        super(card);
    }

    @Override
    public Aurochs copy() {
        return new Aurochs(this);
    }
}
