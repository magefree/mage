package mage.cards.b;

import mage.abilities.effects.common.ReturnFromYourGraveyardToBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactOrEnchantmentCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrilliantRestoration extends CardImpl {

    private static final FilterCard filter = new FilterArtifactOrEnchantmentCard("artifact and enchantment cards");

    public BrilliantRestoration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}{W}{W}");

        // Return all artifact and enchantment cards from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromYourGraveyardToBattlefieldAllEffect(filter));
    }

    private BrilliantRestoration(final BrilliantRestoration card) {
        super(card);
    }

    @Override
    public BrilliantRestoration copy() {
        return new BrilliantRestoration(this);
    }
}
