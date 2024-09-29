package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ViciousClown extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another creature you control with power 2 or less");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public ViciousClown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLOWN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever another creature you control with power 2 or less enters, Vicious Clown gets +2/+0 until end of turn.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new BoostSourceEffect(2, 0, Duration.EndOfTurn), filter
        ));
    }

    private ViciousClown(final ViciousClown card) {
        super(card);
    }

    @Override
    public ViciousClown copy() {
        return new ViciousClown(this);
    }
}
