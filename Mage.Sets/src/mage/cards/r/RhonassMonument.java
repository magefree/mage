package mage.cards.r;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class RhonassMonument extends CardImpl {

    private static final FilterCard filter = new FilterCard("Green creature spells");

    static {
        filter.add(Predicates.and(new ColorPredicate(ObjectColor.GREEN), CardType.CREATURE.getPredicate()));
    }

    public RhonassMonument(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);

        // Green creature spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // Whenever you cast a creature spell, target creature you control gets +2/+2 and gains trample until end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(new BoostTargetEffect(2, 2, Duration.EndOfTurn)
                .setText("target creature you control gets +2/+2"), StaticFilters.FILTER_SPELL_A_CREATURE, false);
        Effect effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains trample until end of turn");
        ability.addEffect(effect);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private RhonassMonument(final RhonassMonument card) {
        super(card);
    }

    @Override
    public RhonassMonument copy() {
        return new RhonassMonument(this);
    }
}
