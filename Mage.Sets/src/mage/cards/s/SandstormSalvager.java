package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.GolemToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SandstormSalvager extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("creature token you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public SandstormSalvager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Sandstorm Salvager enters the battlefield, create a 3/3 colorless Golem artifact creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GolemToken())));

        // {2}, {T}: Put a +1/+1 counter on each creature token you control. They gain trample until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new GainAbilityAllEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, filter));
        this.addAbility(ability);
    }

    private SandstormSalvager(final SandstormSalvager card) {
        super(card);
    }

    @Override
    public SandstormSalvager copy() {
        return new SandstormSalvager(this);
    }
}
