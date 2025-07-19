package mage.cards.n;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class NanoformSentinel extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another target permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public NanoformSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever this creature becomes tapped, untap another target permanent. This ability triggers only once each turn.
        TriggeredAbility ability = new BecomesTappedSourceTriggeredAbility(new UntapTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        ability.setTriggersLimitEachTurn(1);
        this.addAbility(ability);
    }

    private NanoformSentinel(final NanoformSentinel card) {
        super(card);
    }

    @Override
    public NanoformSentinel copy() {
        return new NanoformSentinel(this);
    }
}
