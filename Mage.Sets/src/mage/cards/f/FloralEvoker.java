package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FloralEvoker extends CardImpl {

    public FloralEvoker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Landfall -- Whenever a land you control enters, put a +1/+1 counter on this creature.
        this.addAbility(new LandfallAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));

        // {G}, Discard a creature card: Return target land card from your graveyard to the battlefield tapped.
        Ability ability = new SimpleActivatedAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(true), new ManaCostsImpl<>("{G}")
        );
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(StaticFilters.FILTER_CARD_CREATURE_A)));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_LAND_FROM_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private FloralEvoker(final FloralEvoker card) {
        super(card);
    }

    @Override
    public FloralEvoker copy() {
        return new FloralEvoker(this);
    }
}
