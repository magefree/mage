package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.HumanSoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CosmograndZenith extends CardImpl {

    public CosmograndZenith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you cast your second spell each turn, choose one --
        // * Create two 1/1 white Human Soldier creature tokens.
        Ability ability = new CastSecondSpellTriggeredAbility(new CreateTokenEffect(new HumanSoldierToken(), 2));

        // * Put a +1/+1 counter on each creature you control.
        ability.addMode(new Mode(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        )));
        this.addAbility(ability);
    }

    private CosmograndZenith(final CosmograndZenith card) {
        super(card);
    }

    @Override
    public CosmograndZenith copy() {
        return new CosmograndZenith(this);
    }
}
