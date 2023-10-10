package mage.cards.c;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author magenoxx_at_gmail.com
 */
public final class CastThroughTime extends CardImpl {

    private static final FilterCard filter = new FilterCard("Instant and sorcery spells you control");

    static {
        filter.add(Predicates.or(CardType.INSTANT.getPredicate(), CardType.SORCERY.getPredicate()));
        filter.add(Predicates.not(new AbilityPredicate(ReboundAbility.class))); // So there are not redundant copies being added to each card
    }

    public CastThroughTime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}{U}{U}");

        // Instant and sorcery spells you control have rebound.
        //  (Exile the spell as it resolves if you cast it from your hand. At the beginning of your next upkeep, you may cast that card from exile without paying its mana cost.)
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(new ReboundAbility(), filter)));
    }

    private CastThroughTime(final CastThroughTime card) {
        super(card);
    }

    @Override
    public CastThroughTime copy() {
        return new CastThroughTime(this);
    }
}
