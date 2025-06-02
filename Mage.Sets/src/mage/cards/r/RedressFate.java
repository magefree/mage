package mage.cards.r;

import mage.abilities.effects.common.ReturnFromYourGraveyardToBattlefieldAllEffect;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactOrEnchantmentCard;

import java.util.UUID;

/**
 * @author RobertFrosty
 */
public final class RedressFate extends CardImpl {

    private static final FilterCard filter = new FilterArtifactOrEnchantmentCard("artifact and enchantment cards");

    public RedressFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{W}{W}");

        // Return all artifact and enchantment cards from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromYourGraveyardToBattlefieldAllEffect(filter));
        
        // Miracle {3}{W}
        this.addAbility(new MiracleAbility("{3}{W}"));

    }

    private RedressFate(final RedressFate card) {
        super(card);
    }

    @Override
    public RedressFate copy() {
        return new RedressFate(this);
    }
}
