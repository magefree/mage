package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class FleetingMemories extends CardImpl {

    public FleetingMemories(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");

        // When Fleeting Memories enters the battlefield, investigate.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new InvestigateEffect(), false));

        // Whenever you sacrifice a Clue, target player puts the top three cards of their graveyard into their graveyard.
        Ability ability = new SacrificePermanentTriggeredAbility(new MillCardsTargetEffect(3), StaticFilters.FILTER_CONTROLLED_CLUE);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private FleetingMemories(final FleetingMemories card) {
        super(card);
    }

    @Override
    public FleetingMemories copy() {
        return new FleetingMemories(this);
    }
}
