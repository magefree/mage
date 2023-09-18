package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SilvarDevourerOfTheFree extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.HUMAN, "a Human");

    public SilvarDevourerOfTheFree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Partner with Trynn, Champion of Freedom
        this.addAbility(new PartnerWithAbility("Trynn, Champion of Freedom"));

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Sacrifice a Human: Put a +1/+1 counter on Silvar, Devourer of the Free. It gains indestructible until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new SacrificeTargetCost(new TargetControlledPermanent(filter))
        );
        ability.addEffect(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains indestructible until end of turn"));
        this.addAbility(ability);
    }

    private SilvarDevourerOfTheFree(final SilvarDevourerOfTheFree card) {
        super(card);
    }

    @Override
    public SilvarDevourerOfTheFree copy() {
        return new SilvarDevourerOfTheFree(this);
    }
}
