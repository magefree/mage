package mage.cards.g;

import java.util.UUID;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.target.common.TargetAttackingOrBlockingCreature;
import mage.abilities.Ability;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class GideonsMemorial extends CardImpl {

    private static final FilterSpell filterSpell = new FilterSpell("a planeswalker spell");

    static {
        filterSpell.add(CardType.PLANESWALKER.getPredicate());
    }

    public GideonsMemorial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);

        // Creature tokens you control get +1/+0 and have vigilance.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
            1, 0, Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURE_TOKENS
        ));
        ability.addEffect(new GainAbilityControlledEffect(
            VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURE_TOKENS
        ).setText("and have vigilance"));
        this.addAbility(ability);

        // {T}: Add one mana of any color. Spend this mana only to cast a planeswalker spell.
        this.addAbility(new ConditionalAnyColorManaAbility(new TapSourceCost(), 1, new ConditionalSpellManaBuilder(filterSpell), true));

        // {1}{W}, Discard this card: It deals 4 damage to target attacking or blocking creature.
        Ability ability2 = new SimpleActivatedAbility(
            new DamageTargetEffect(4),
            new ManaCostsImpl<>("{1}{W}")
        );
        ability2.addCost(new DiscardSourceCost());
        ability2.addTarget(new TargetAttackingOrBlockingCreature());
        this.addAbility(ability2);
    }

    private GideonsMemorial(final GideonsMemorial card) {
        super(card);
    }

    @Override
    public GideonsMemorial copy() {
        return new GideonsMemorial(this);
    }
}
