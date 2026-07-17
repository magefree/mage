package mage.cards.z;

import mage.MageInt;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FirebendingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZhaoRuthlessAdmiral extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("another permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ZhaoRuthlessAdmiral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B/R}{B/R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Firebending 2
        this.addAbility(new FirebendingAbility(2));

        // Whenever you sacrifice another permanent, creatures you control get +1/+0 until end of turn.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                new BoostControlledEffect(1, 0, Duration.EndOfTurn), filter
        ));
    }

    private ZhaoRuthlessAdmiral(final ZhaoRuthlessAdmiral card) {
        super(card);
    }

    @Override
    public ZhaoRuthlessAdmiral copy() {
        return new ZhaoRuthlessAdmiral(this);
    }
}
