
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
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
 * @author fireshoes
 */
public final class AngelicCaptain extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("other attacking Ally");

    static {
        filter.add(new SubtypePredicate(SubType.ALLY));
        filter.add(new AnotherPredicate());
    }

    public AngelicCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{W}");
        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Angelic Captain attacks, it gets +1/+1 until end of turn for each other attacking Ally.
        PermanentsOnBattlefieldCount value = new PermanentsOnBattlefieldCount(filter);
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(value, value, Duration.EndOfTurn, true), false));
    }

    public AngelicCaptain(final AngelicCaptain card) {
        super(card);
    }

    @Override
    public AngelicCaptain copy() {
        return new AngelicCaptain(this);
    }
}
