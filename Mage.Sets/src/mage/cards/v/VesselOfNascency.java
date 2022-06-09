package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.effects.common.RevealLibraryPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

/**
 *
 * @author awjackson
 */
public final class VesselOfNascency extends CardImpl {

    private static final FilterCard filter = new FilterCard("an artifact, creature, enchantment, land, or planeswalker card");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                CardType.LAND.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public VesselOfNascency(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // {1}{G}, Sacrifice Vessel of Nascency: Reveal the top four cards of your library. You may put an artifact, creature, enchantment, land, or
        // planeswalker card from among them into your hand. Put the rest into your graveyard.
        Ability ability = new SimpleActivatedAbility(
                new RevealLibraryPickControllerEffect(4, 1, filter, PutCards.HAND, PutCards.GRAVEYARD),
                new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private VesselOfNascency(final VesselOfNascency card) {
        super(card);
    }

    @Override
    public VesselOfNascency copy() {
        return new VesselOfNascency(this);
    }
}
