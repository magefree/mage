package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SorcerersBroom extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public SorcerersBroom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you sacrifice another permanent, you may pay {3}. If you do, create a token that's a copy of Sorcerer's Broom.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                new DoIfCostPaid(new CreateTokenCopySourceEffect(), new GenericManaCost(3)),
                filter
        ));
    }

    private SorcerersBroom(final SorcerersBroom card) {
        super(card);
    }

    @Override
    public SorcerersBroom copy() {
        return new SorcerersBroom(this);
    }
}
