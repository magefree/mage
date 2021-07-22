package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TomeShredder extends CardImpl {

    private static final FilterCard filter
            = new FilterInstantOrSorceryCard("an instant or sorcery card from your graveyard");

    public TomeShredder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {T}, Exile an instant or sorcery card from your graveyard: Put a +1/+1 counter on Tome Shredder.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new TapSourceCost()
        );
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(filter)));
        this.addAbility(ability);
    }

    private TomeShredder(final TomeShredder card) {
        super(card);
    }

    @Override
    public TomeShredder copy() {
        return new TomeShredder(this);
    }
}
