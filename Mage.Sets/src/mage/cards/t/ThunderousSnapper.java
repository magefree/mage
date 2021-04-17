package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThunderousSnapper extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell with mana value 5 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 4));
    }

    public ThunderousSnapper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G/U}{G/U}{G/U}{G/U}");

        this.subtype.add(SubType.TURTLE);
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you cast a spell with converted mana cost 5 or greater, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter, false
        ));
    }

    private ThunderousSnapper(final ThunderousSnapper card) {
        super(card);
    }

    @Override
    public ThunderousSnapper copy() {
        return new ThunderousSnapper(this);
    }
}
