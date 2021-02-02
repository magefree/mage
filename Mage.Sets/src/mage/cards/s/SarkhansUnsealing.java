package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author TheElk801
 */
public final class SarkhansUnsealing extends CardImpl {

    private static final FilterSpell filter = new FilterCreatureSpell("a creature spell with power 4, 5, or 6");
    private static final FilterSpell filter2 = new FilterCreatureSpell("a creature spell with power 7 or greater");
    private static final FilterPermanent filter3 = new FilterPermanent();

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 7));
        filter2.add(new PowerPredicate(ComparisonType.MORE_THAN, 6));
        filter3.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
        filter3.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public SarkhansUnsealing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // Whenever you cast a creature spell with power 4, 5, or 6, Sarkhan's Unsealing deals 4 damage to any target.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DamageTargetEffect(4),
                filter, false
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // Whenever you cast a creature spell with power 7 or greater, Sarkhan's Unsealing deals 4 damage to each opponent and each creature and planeswalker they control.
        ability = new SpellCastControllerTriggeredAbility(
                new DamagePlayersEffect(4, TargetController.OPPONENT)
                        .setText("{this} deals 4 damage to each opponent"),
                filter2, false
        );
        ability.addEffect(
                new DamageAllEffect(4, filter3)
                        .setText("and each creature and planeswalker they control")
        );
        this.addAbility(ability);
    }

    private SarkhansUnsealing(final SarkhansUnsealing card) {
        super(card);
    }

    @Override
    public SarkhansUnsealing copy() {
        return new SarkhansUnsealing(this);
    }
}
