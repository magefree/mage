package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.TreasureToken;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TokkaAndRahzarUnsupervised extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another nontoken creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
        filter.add(CardType.CREATURE.getPredicate());
    }

    public TokkaAndRahzarUnsupervised(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TURTLE);
        this.subtype.add(SubType.WOLF);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever another nontoken creature you control leaves the battlefield, put a +1/+1 counter on Tokka & Rahzar and create a Treasure token. This ability triggers only once each turn.
        Ability ability = new LeavesBattlefieldAllTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter
        ).setTriggersLimitEachTurn(1);
        ability.addEffect(new CreateTokenEffect(new TreasureToken()).concatBy("and"));
        this.addAbility(ability);
    }

    private TokkaAndRahzarUnsupervised(final TokkaAndRahzarUnsupervised card) {
        super(card);
    }

    @Override
    public TokkaAndRahzarUnsupervised copy() {
        return new TokkaAndRahzarUnsupervised(this);
    }
}
