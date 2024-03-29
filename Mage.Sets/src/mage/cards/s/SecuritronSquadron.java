package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.SquadAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SecuritronSquadron extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature token");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public SecuritronSquadron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Squad {3}
        this.addAbility(new SquadAbility(new GenericManaCost(3)));

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever a creature token enters the battlefield under your control, put a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                filter,
                false,
                SetTargetPointer.PERMANENT
        ));
    }

    private SecuritronSquadron(final SecuritronSquadron card) {
        super(card);
    }

    @Override
    public SecuritronSquadron copy() {
        return new SecuritronSquadron(this);
    }
}
