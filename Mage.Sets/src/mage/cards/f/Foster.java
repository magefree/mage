package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class Foster extends CardImpl {

    public Foster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        // Whenever a creature you control dies, you may pay {1}. If you do, reveal cards from the top of your library until you reveal a creature card. Put that card into your hand and the rest into your graveyard.
        Ability ability = new DiesCreatureTriggeredAbility(
                new DoIfCostPaid(new RevealCardsFromLibraryUntilEffect(StaticFilters.FILTER_CARD_CREATURE, Zone.HAND, Zone.GRAVEYARD), new GenericManaCost(1)),
                false, StaticFilters.FILTER_CONTROLLED_A_CREATURE);
        this.addAbility(ability);
    }

    private Foster(final Foster card) {
        super(card);
    }

    @Override
    public Foster copy() {
        return new Foster(this);
    }
}
