package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantBeCounteredControlledEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;

/**
 * @author TheElk801
 */
public final class ThryxTheSuddenStorm extends CardImpl {

    private static final FilterCard filter = new FilterCard();
    private static final FilterSpell filter2 = new FilterSpell();

    static {
        Predicate predicate = new ManaValuePredicate(ComparisonType.MORE_THAN, 4);
        filter.add(predicate);
        filter2.add(predicate);
    }

    public ThryxTheSuddenStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Spells you cast with converted mana cost 5 or greater cost {1} less to cast and can't be countered.
        Ability ability = new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)
                .setText("spells you cast with mana value 5 or greater cost {1} less to cast"));
        ability.addEffect(new CantBeCounteredControlledEffect(
                filter2, null, Duration.WhileOnBattlefield
        ).setText("and can't be countered"));
        this.addAbility(ability);
    }

    private ThryxTheSuddenStorm(final ThryxTheSuddenStorm card) {
        super(card);
    }

    @Override
    public ThryxTheSuddenStorm copy() {
        return new ThryxTheSuddenStorm(this);
    }
}
