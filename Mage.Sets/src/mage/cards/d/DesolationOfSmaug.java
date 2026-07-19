package mage.cards.d;

import java.util.UUID;

import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.mana.AddConditionalManaOfAnyColorEffect;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author muz
 */
public final class DesolationOfSmaug extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("non-Dragon creature");
    private static final FilterSpell filter2 = new FilterSpell("Dragon spells");

    static {
        filter.add(Predicates.not(SubType.DRAGON.getPredicate()));
        filter2.add(SubType.DRAGON.getPredicate());
    }

    public DesolationOfSmaug(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");

        // Desolation of Smaug deals 3 damage to each non-Dragon creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(3, filter));

        // Add four mana in any combination of colors. Spend this mana only to cast Dragon spells.
        this.getSpellAbility().addEffect(
            new AddConditionalManaOfAnyColorEffect(
                StaticValue.get(4),
                StaticValue.get(4),
                new ConditionalSpellManaBuilder(filter2),
                false
            )
        );
    }

    private DesolationOfSmaug(final DesolationOfSmaug card) {
        super(card);
    }

    @Override
    public DesolationOfSmaug copy() {
        return new DesolationOfSmaug(this);
    }
}
