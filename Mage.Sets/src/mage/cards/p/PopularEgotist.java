package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PopularEgotist extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("another creature or enchantment");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public PopularEgotist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {1}{B}, Sacrifice another creature or enchantment: Popular Egotist gains indestructible until end of turn. Tap it.
        Ability ability = new SimpleActivatedAbility(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new SacrificeTargetCost(filter));
        ability.addEffect(new TapSourceEffect().setText("tap it"));
        this.addAbility(ability);

        // Whenever you sacrifice a permanent, target opponent loses 1 life and you gain 1 life.
        ability = new SacrificePermanentTriggeredAbility(
                new LoseLifeTargetEffect(1), StaticFilters.FILTER_PERMANENT
        );
        ability.addTarget(new TargetOpponent());
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private PopularEgotist(final PopularEgotist card) {
        super(card);
    }

    @Override
    public PopularEgotist copy() {
        return new PopularEgotist(this);
    }
}
