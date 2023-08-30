package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IcebergCancrix extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SuperType.SNOW.getPredicate());
    }

    public IcebergCancrix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.CRAB);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Whenever another snow permanent enters the battlefield under your control, you may have target player put the top two cards of their library into their graveyard.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new MillCardsTargetEffect(2), filter,
                true, "Whenever another snow permanent enters the battlefield under your control, " +
                "you may have target player mill two cards."
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private IcebergCancrix(final IcebergCancrix card) {
        super(card);
    }

    @Override
    public IcebergCancrix copy() {
        return new IcebergCancrix(this);
    }
}
