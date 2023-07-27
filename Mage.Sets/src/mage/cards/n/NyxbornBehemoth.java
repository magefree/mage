package mage.cards.n;

import mage.MageInt;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.TotalPermanentsManaValue;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class NyxbornBehemoth extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledEnchantmentPermanent("noncreature enchantments you control");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    private static final TotalPermanentsManaValue xValue = new TotalPermanentsManaValue(filter);

    public NyxbornBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{10}{G}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // This spell costs {X} less to cast, where X is the total mana value of noncreature enchantments you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SpellCostReductionSourceEffect(xValue)
            ).addHint(xValue.getHint()).setRuleAtTheTop(true)
        );

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // {1}{G}, Sacrifice another enchantment: Nyxborn Behemoth gains indestructible until end of turn.
        ActivatedAbility ability = new SimpleActivatedAbility(
            Zone.BATTLEFIELD,
            new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn),
            new ManaCostsImpl<>("{1}{G}")
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_ENCHANTMENT_SHORT_TEXT));

        this.addAbility(ability);
    }

    private NyxbornBehemoth(final NyxbornBehemoth card) {
        super(card);
    }

    @Override
    public NyxbornBehemoth copy() {
        return new NyxbornBehemoth(this);
    }
}
