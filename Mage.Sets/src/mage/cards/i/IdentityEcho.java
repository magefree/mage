package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class IdentityEcho extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature or planeswalker card");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.PLANESWALKER.getPredicate()));
    }

    public IdentityEcho(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // {3}{R}: Exile target creature or planeswalker you control. Reveal cards from the top of your library until you reveal a creature or planeswalker card. Put that card onto the battlefield and the rest on the bottom of your library in a random order. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new ExileTargetEffect(), new ManaCostsImpl<>("{3}{R}"));
        ability.addEffect(new RevealCardsFromLibraryUntilEffect(filter, PutCards.BATTLEFIELD, PutCards.BOTTOM_RANDOM)
                .concatBy("."));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_CREATURE_OR_PLANESWALKER));
        this.addAbility(ability);
    }

    private IdentityEcho(final IdentityEcho card) {
        super(card);
    }

    @Override
    public IdentityEcho copy() {
        return new IdentityEcho(this);
    }
}
