package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrallinSkysharkRider extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SHARK, "Shark");

    public BrallinSkysharkRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Partner with Shabraz, the Skyshark
        this.addAbility(new PartnerWithAbility("Shabraz, the Skyshark"));

        // Whenever you discard a card, put a +1/+1 counter on Brallin, Skyshark Rider and it deals 1 damage to each opponent.
        Ability ability = new DiscardCardControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false
        );
        ability.addEffect(new DamagePlayersEffect(
                1, TargetController.OPPONENT, "and it"
        ));
        this.addAbility(ability);

        // {R}: Target Shark gains trample until end of turn.
        ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{R}"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private BrallinSkysharkRider(final BrallinSkysharkRider card) {
        super(card);
    }

    @Override
    public BrallinSkysharkRider copy() {
        return new BrallinSkysharkRider(this);
    }
}
