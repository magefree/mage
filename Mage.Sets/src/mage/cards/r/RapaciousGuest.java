package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RapaciousGuest extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount(false);

    public RapaciousGuest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever one or more creatures you control deal combat damage to a player, create a Food token.
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(
                new CreateTokenEffect(new FoodToken())
        ));

        // Whenever you sacrifice a Food, put a +1/+1 counter on Rapacious Guest.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_CONTROLLED_FOOD
        ));

        // When Rapacious Guest leaves the battlefield, target opponent loses life equal to its power.
        Ability ability = new LeavesBattlefieldTriggeredAbility(
                new LoseLifeTargetEffect(xValue).setText("target opponent loses life equal to its power."),
                false
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private RapaciousGuest(final RapaciousGuest card) {
        super(card);
    }

    @Override
    public RapaciousGuest copy() {
        return new RapaciousGuest(this);
    }
}