package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.BecomesTargetAnyTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class DaruSpiritualist extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(SubType.CLERIC, "a Cleric creature you control");

    public DaruSpiritualist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a Cleric creature you control becomes the target of a spell or ability, it gets +0/+2 until end of turn.
        this.addAbility(new BecomesTargetAnyTriggeredAbility(new BoostTargetEffect(0, 2, Duration.EndOfTurn), filter));
    }

    private DaruSpiritualist(final DaruSpiritualist card) {
        super(card);
    }

    @Override
    public DaruSpiritualist copy() {
        return new DaruSpiritualist(this);
    }
}
