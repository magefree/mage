
package mage.cards.r;

import java.util.UUID;
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
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class RhonassMonument extends CardImpl {

    private static final FilterCard filter = new FilterCard("Green creature spells");
    private static final FilterSpell filter2 = new FilterSpell("a creature spell");

    static {
        filter.add(Predicates.and(new ColorPredicate(ObjectColor.GREEN), CardType.CREATURE.getPredicate()));
    }

    static {
        filter2.add(CardType.CREATURE.getPredicate());
    }

    public RhonassMonument(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);

        // Green creature spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));

        // Whenever you cast a creature spell, target creature you control gets +2/+2 and gains trample until end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(new BoostTargetEffect(2, 2, Duration.EndOfTurn), filter2, false);
        Effect effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText(" and gains trample until end of turn");
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
