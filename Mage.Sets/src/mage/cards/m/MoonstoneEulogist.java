package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.BloodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoonstoneEulogist extends CardImpl {

    public MoonstoneEulogist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.BAT);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a creature an opponent controls dies, you create a Blood token.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new CreateTokenEffect(new BloodToken()).setText("you create a Blood token"),
                false, StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE
        ));

        // Whenever you sacrifice an artifact, put a +1/+1 counter on Moonstone Eulogist and you gain 1 life.
        Ability ability = new SacrificePermanentTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_PERMANENT_ARTIFACT_AN
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private MoonstoneEulogist(final MoonstoneEulogist card) {
        super(card);
    }

    @Override
    public MoonstoneEulogist copy() {
        return new MoonstoneEulogist(this);
    }
}
