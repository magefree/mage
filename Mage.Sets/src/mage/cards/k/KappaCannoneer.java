package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ImproviseAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KappaCannoneer extends CardImpl {

    public KappaCannoneer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}{U}");

        this.subtype.add(SubType.TURTLE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Improvise
        this.addAbility(new ImproviseAbility());

        // Ward {4}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{4}")));

        // Whenever an artifact enters the battlefield under your control, put a +1/+1 counter on Kappa Cannoneer and it can't be blocked this turn.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN
        );
        ability.addEffect(new CantBeBlockedSourceEffect(Duration.EndOfTurn)
                .setText("and it can't be blocked this turn"));
        this.addAbility(ability);
    }

    private KappaCannoneer(final KappaCannoneer card) {
        super(card);
    }

    @Override
    public KappaCannoneer copy() {
        return new KappaCannoneer(this);
    }
}
