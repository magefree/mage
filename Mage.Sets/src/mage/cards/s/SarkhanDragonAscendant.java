package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.BeholdDragonCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.AddCardSubTypeSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SarkhanDragonAscendant extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.DRAGON, "a Dragon you control");

    public SarkhanDragonAscendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Sarkhan enters, you may behold a Dragon. If you do, create a Treasure token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoIfCostPaid(new CreateTokenEffect(new TreasureToken()), new BeholdDragonCost())
        ));

        // Whenever a Dragon you control enters, put a +1/+1 counter on Sarkhan. Until end of turn, Sarkhan becomes a Dragon in addition to its other types and gains flying.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter
        );
        ability.addEffect(new AddCardSubTypeSourceEffect(
                Duration.EndOfTurn, SubType.DRAGON
        ).setText("until end of turn, {this} becomes a Dragon in addition to its other types"));
        ability.addEffect(new GainAbilitySourceEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains flying"));
        this.addAbility(ability);
    }

    private SarkhanDragonAscendant(final SarkhanDragonAscendant card) {
        super(card);
    }

    @Override
    public SarkhanDragonAscendant copy() {
        return new SarkhanDragonAscendant(this);
    }
}
