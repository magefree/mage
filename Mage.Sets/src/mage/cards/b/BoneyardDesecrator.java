package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SacrificedPermanentCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BoneyardDesecrator extends CardImpl {

    public BoneyardDesecrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // {1}{B}, Sacrifice another creature: Put a +1/+1 counter on Boneyard Desecrator. If an outlaw was sacrificed this way, create a Treasure token.
        Effect effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance());
        Ability ability = new SimpleActivatedAbility(effect, new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        ability.addEffect(new ConditionalOneShotEffect(
                new CreateTokenEffect(new TreasureToken()),
                new SacrificedPermanentCondition(StaticFilters.FILTER_ANOTHER_CREATURE, "an outlaw was sacrificed this way")
        ));
        this.addAbility(ability);
    }

    private BoneyardDesecrator(final BoneyardDesecrator card) {
        super(card);
    }

    @Override
    public BoneyardDesecrator copy() {
        return new BoneyardDesecrator(this);
    }
}