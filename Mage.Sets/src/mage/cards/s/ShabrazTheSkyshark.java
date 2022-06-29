package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShabrazTheSkyshark extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.HUMAN, "Human");

    public ShabrazTheSkyshark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHARK);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Partner with Brallin, Skyshark Rider
        this.addAbility(new PartnerWithAbility("Brallin, Skyshark Rider"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you draw a card, put a +1/+1 counter on Shabraz, the Skyshark and you gain 1 life.
        Ability ability = new DrawCardControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);

        // {W/U}: Target Human gains flying until end of turn.
        ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{W/U}"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ShabrazTheSkyshark(final ShabrazTheSkyshark card) {
        super(card);
    }

    @Override
    public ShabrazTheSkyshark copy() {
        return new ShabrazTheSkyshark(this);
    }
}
