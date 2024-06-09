package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MischievousPup extends CardImpl {

    private static final FilterControlledPermanent filter =
            new FilterControlledPermanent("other target permanent you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public MischievousPup(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Mischievous Pup enters the battlefield, return up to one other target permanent you control to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect());
        ability.addTarget(new TargetControlledPermanent(0, 1, filter, false));
        this.addAbility(ability);
    }

    private MischievousPup(final MischievousPup card) {
        super(card);
    }

    @Override
    public MischievousPup copy() {
        return new MischievousPup(this);
    }
}
