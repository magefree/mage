package mage.cards.a;

import mage.MageInt;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AwakeningOfVituGhazi extends CardImpl {

    public AwakeningOfVituGhazi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}{G}");

        // Put nine +1/+1 counters on target land you control. It becomes a legendary 0/0 Elemental creature with haste named Vitu-Ghazi. It's still a land.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(9)));
        this.getSpellAbility().addEffect(new BecomesCreatureTargetEffect(
                new AwakeningOfVituGhaziToken(), false, true, Duration.Custom, true
        ).setText("It becomes a legendary 0/0 Elemental creature with haste named Vitu-Ghazi. It's still a land."));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND));
    }

    private AwakeningOfVituGhazi(final AwakeningOfVituGhazi card) {
        super(card);
    }

    @Override
    public AwakeningOfVituGhazi copy() {
        return new AwakeningOfVituGhazi(this);
    }
}

class AwakeningOfVituGhaziToken extends TokenImpl {

    AwakeningOfVituGhaziToken() {
        super("Vitu-Ghazi", "legendary 0/0 Elemental creature with haste named Vitu-Ghazi");
        this.supertype.add(SuperType.LEGENDARY);
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        this.addAbility(HasteAbility.getInstance());
    }

    private AwakeningOfVituGhaziToken(final AwakeningOfVituGhaziToken token) {
        super(token);
    }

    public AwakeningOfVituGhaziToken copy() {
        return new AwakeningOfVituGhaziToken(this);
    }
}
