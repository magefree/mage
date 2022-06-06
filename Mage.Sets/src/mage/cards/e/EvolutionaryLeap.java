package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class EvolutionaryLeap extends CardImpl {

    public EvolutionaryLeap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // {G}, Sacrifice a creature: Reveal cards from the top of your library until you reveal a creature card. Put that card into your hand and the rest on the bottom of your library in a random order.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RevealCardsFromLibraryUntilEffect(StaticFilters.FILTER_CARD_CREATURE, Zone.HAND, Zone.LIBRARY), new ManaCostsImpl<>("{G}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        this.addAbility(ability);
    }

    private EvolutionaryLeap(final EvolutionaryLeap card) {
        super(card);
    }

    @Override
    public EvolutionaryLeap copy() {
        return new EvolutionaryLeap(this);
    }
}
