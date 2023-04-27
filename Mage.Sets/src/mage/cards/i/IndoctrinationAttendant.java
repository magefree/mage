package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.PhyrexianMiteToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IndoctrinationAttendant extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("another permanent you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public IndoctrinationAttendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Toxic 1
        this.addAbility(new ToxicAbility(1));

        // When Indoctrination Attendant enters the battlefield, you may return another permanent you control to its owner's hand. If you do, create a 1/1 colorless Phyrexian Mite artifact creature token with toxic 1 and "This creature can't block."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new CreateTokenEffect(new PhyrexianMiteToken()),
                new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(filter))
        )));
    }

    private IndoctrinationAttendant(final IndoctrinationAttendant card) {
        super(card);
    }

    @Override
    public IndoctrinationAttendant copy() {
        return new IndoctrinationAttendant(this);
    }
}
