package mage.cards.t;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.effects.common.replacement.GainDoubleLifeReplacementEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheWindCrystal extends CardImpl {

    private static final FilterCard filter = new FilterCard("white spells");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public TheWindCrystal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);

        // White spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // If you would gain life, you gain twice that much life instead.
        this.addAbility(new SimpleStaticAbility(new GainDoubleLifeReplacementEffect()));

        // {4}{W}{W}, {T}: Creatures you control gain flying and lifelink until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("creatures you control gain flying"), new ManaCostsImpl<>("{4}{W}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new GainAbilityControlledEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and lifelink until end of turn"));
        this.addAbility(ability);
    }

    private TheWindCrystal(final TheWindCrystal card) {
        super(card);
    }

    @Override
    public TheWindCrystal copy() {
        return new TheWindCrystal(this);
    }
}
