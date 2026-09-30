package mage.cards.c;

import java.util.UUID;

import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.BeholdCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetSpell;

/**
 *
 * @author muz
 */
public final class Countersculpt extends CardImpl {

    public Countersculpt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}");

        // As an additional cost to cast this spell, behold a Jace or pay {1}.
        this.getSpellAbility().addCost(new OrCost(
            "behold a Jace or pay {1}", new BeholdCost(SubType.JACE), new GenericManaCost(1)
        ));

        // Counter target spell. Empower Jace 1.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new EmpowerJaceEffect(1));
    }

    private Countersculpt(final Countersculpt card) {
        super(card);
    }

    @Override
    public Countersculpt copy() {
        return new Countersculpt(this);
    }
}
