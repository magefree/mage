package mage.cards.r;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RockfaceVillage extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("Lizard, Mouse, Otter, or Raccoon you control");

    static {
        filter.add(Predicates.or(
                SubType.LIZARD.getPredicate(),
                SubType.MOUSE.getPredicate(),
                SubType.OTTER.getPredicate(),
                SubType.RACCOON.getPredicate()
        ));
    }

    public RockfaceVillage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");


        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add {R}. Spend this mana only to cast a creature spell.
        this.addAbility(new ConditionalColoredManaAbility(
                Mana.RedMana(1), new ConditionalSpellManaBuilder(StaticFilters.FILTER_SPELL_A_CREATURE)
        ));

        // {R}, {T}: Target Lizard, Mouse, Otter, or Raccoon you control gets +1/+0 and gains haste until end of turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new BoostTargetEffect(1, 0)
                .setText("target Lizard, Mouse, Otter, or Raccoon you control gets +1/+0"), new ManaCostsImpl<>("{R}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains haste until end of turn"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private RockfaceVillage(final RockfaceVillage card) {
        super(card);
    }

    @Override
    public RockfaceVillage copy() {
        return new RockfaceVillage(this);
    }
}
