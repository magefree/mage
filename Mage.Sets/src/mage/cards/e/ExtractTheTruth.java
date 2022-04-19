package mage.cards.e;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetOpponent;

/**
 *
 * @author weirddan455
 */
public final class ExtractTheTruth extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature, enchantment, or planeswalker card");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.ENCHANTMENT.getPredicate(), CardType.PLANESWALKER.getPredicate()));
    }

    public ExtractTheTruth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Choose one—
        // • Target opponent reveals their hand. You may choose a creature, enchantment, or planeswalker card from it. That player discards that card.
        DiscardCardYouChooseTargetEffect effect = new DiscardCardYouChooseTargetEffect(filter);
        effect.setOptional(true);
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetOpponent());

        // • Target opponent sacrifices an enchantment.
        this.getSpellAbility().addMode(new Mode(new SacrificeEffect(StaticFilters.FILTER_PERMANENT_ENCHANTMENT, 1, "Target opponent")).addTarget(new TargetOpponent()));
    }

    private ExtractTheTruth(final ExtractTheTruth card) {
        super(card);
    }

    @Override
    public ExtractTheTruth copy() {
        return new ExtractTheTruth(this);
    }
}
