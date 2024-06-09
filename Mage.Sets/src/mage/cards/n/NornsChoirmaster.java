package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksAllTriggeredAbility;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.CommanderPredicate;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class NornsChoirmaster extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a commander you control");

    static {
        filter.add(CommanderPredicate.instance);
    }

    public NornsChoirmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.PHYREXIAN, SubType.ANGEL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever a commander you control enters the battlefield or attacks, proliferate.
        this.addAbility(new EntersBattlefieldOrAttacksAllTriggeredAbility(new ProliferateEffect(true), filter));
    }

    private NornsChoirmaster(final NornsChoirmaster card) {
        super(card);
    }

    @Override
    public NornsChoirmaster copy() {
        return new NornsChoirmaster(this);
    }
}
