package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ItThatHeraldsTheEnd extends CardImpl {

    private static final FilterCard filter = new FilterCard("colorless spells you cast with mana value 7 or greater");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("colorless creatures");

    static {
        filter.add(ColorlessPredicate.instance);
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 6));
        filter2.add(ColorlessPredicate.instance);
    }

    public ItThatHeraldsTheEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{C}");

        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Colorless spells you cast with mana value 7 or greater cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionAllEffect(filter, 1)));

        // Other colorless creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter2, true
        )));
    }

    private ItThatHeraldsTheEnd(final ItThatHeraldsTheEnd card) {
        super(card);
    }

    @Override
    public ItThatHeraldsTheEnd copy() {
        return new ItThatHeraldsTheEnd(this);
    }
}
