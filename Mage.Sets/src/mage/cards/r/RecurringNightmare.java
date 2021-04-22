package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.ReturnToHandFromBattlefieldSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class RecurringNightmare extends CardImpl {

    public RecurringNightmare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // Sacrifice a creature, Return Recurring Nightmare to its owner's hand: Return target creature card from your graveyard to the battlefield. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD,
                new ReturnFromGraveyardToBattlefieldTargetEffect(),
                new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT))
        );
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        ability.addCost(new ReturnToHandFromBattlefieldSourceCost());
        this.addAbility(ability);
    }

    private RecurringNightmare(final RecurringNightmare card) {
        super(card);
    }

    @Override
    public RecurringNightmare copy() {
        return new RecurringNightmare(this);
    }
}
