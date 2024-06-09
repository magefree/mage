package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author TheElk801
 */
public final class RiverSneak extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another Merfolk");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SubType.MERFOLK.getPredicate());
    }

    public RiverSneak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // River Sneak can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());

        // Whenever another Merfolk enters the battlefield under your control, River Sneak gets +1/+1 until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(1, 1, Duration.EndOfTurn), filter, false));
    }

    private RiverSneak(final RiverSneak card) {
        super(card);
    }

    @Override
    public RiverSneak copy() {
        return new RiverSneak(this);
    }
}
