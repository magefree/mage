package mage.cards.d;

import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CastFromHandWithoutPayingManaCostEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

/**
 * @author balazskristof
 */
public final class Dracogenesis extends CardImpl {

    public static final FilterCard filter = new FilterCard("Dragon spells");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(SubType.DRAGON.getPredicate());
    }

    public Dracogenesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{6}{R}{R}");

        // You may cast Dragon spells without paying their mana costs.
        this.addAbility(new SimpleStaticAbility(new CastFromHandWithoutPayingManaCostEffect(filter, false)));
    }

    private Dracogenesis(final Dracogenesis card) {
        super(card);
    }

    @Override
    public Dracogenesis copy() {
        return new Dracogenesis(this);
    }
}
