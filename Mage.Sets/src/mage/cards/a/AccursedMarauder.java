package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AccursedMarauder extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("a nontoken creature");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public AccursedMarauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Accursed Marauder enters the battlefield, each player sacrifices a nontoken creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeAllEffect(1, filter)));
    }

    private AccursedMarauder(final AccursedMarauder card) {
        super(card);
    }

    @Override
    public AccursedMarauder copy() {
        return new AccursedMarauder(this);
    }
}
