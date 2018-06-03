
package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.ReturnToHandFromBattlefieldSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class RecurringNightmare extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card");
    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent("creature");

    public RecurringNightmare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}");

        // Sacrifice a creature, Return Recurring Nightmare to its owner's hand: Return target creature card from your graveyard to the battlefield. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(), new SacrificeTargetCost(new TargetControlledPermanent(filter2)));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        ability.addCost(new ReturnToHandFromBattlefieldSourceCost());
        this.addAbility(ability);
    }

    public RecurringNightmare(final RecurringNightmare card) {
        super(card);
    }

    @Override
    public RecurringNightmare copy() {
        return new RecurringNightmare(this);
    }
}
