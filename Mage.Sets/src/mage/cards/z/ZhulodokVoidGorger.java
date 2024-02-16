package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZhulodokVoidGorger extends CardImpl {

    private static final FilterCard filter = new FilterCard("colorless spells you cast from your hand with mana value 7 or greater");

    static {
        filter.add(ColorlessPredicate.instance);
        filter.add(new CastFromZonePredicate(Zone.HAND));
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 6));
    }

    public ZhulodokVoidGorger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{C}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(7);
        this.toughness = new MageInt(4);

        // Colorless spells you cast from your hand with mana value 7 or greater have "Cascade, cascade."
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(new CascadeAbility(false), filter)
                .setText("colorless spells you cast from your hand with mana value 7"));
        ability.addEffect(new GainAbilityControlledSpellsEffect(new CascadeAbility(false), filter)
                .setText("or greater have \"Cascade, cascade.\""));
        this.addAbility(ability);
    }

    private ZhulodokVoidGorger(final ZhulodokVoidGorger card) {
        super(card);
    }

    @Override
    public ZhulodokVoidGorger copy() {
        return new ZhulodokVoidGorger(this);
    }
}
