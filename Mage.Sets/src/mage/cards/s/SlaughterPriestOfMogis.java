package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SlaughterPriestOfMogis extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("another creature or an enchantment");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
        filter.add(AnotherPredicate.instance);
    }

    public SlaughterPriestOfMogis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you sacrifice a permanent, Slaughter-Priest of Mogis gets +2/+0 until end of turn.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                new BoostSourceEffect(2, 0, Duration.EndOfTurn), StaticFilters.FILTER_PERMANENT
        ));

        // {2}, Sacrifice another creature or enchantment: Slaughter-Priest of Mogis gains first strike until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn),
                new GenericManaCost(2));
        ability.addCost(new SacrificeTargetCost(filter));
        this.addAbility(ability);
    }

    private SlaughterPriestOfMogis(final SlaughterPriestOfMogis card) {
        super(card);
    }

    @Override
    public SlaughterPriestOfMogis copy() {
        return new SlaughterPriestOfMogis(this);
    }
}
