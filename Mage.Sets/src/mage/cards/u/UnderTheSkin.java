package mage.cards.u;

import mage.abilities.effects.common.ReturnCardChosenFromGraveyardEffect;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;

import java.util.UUID;

/**
 * @author alma555
 */
public final class UnderTheSkin extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("permanent card from your graveyard");

    public UnderTheSkin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Manifest Dread
        this.getSpellAbility().addEffect(new ManifestDreadEffect());

        // You may return a permanent card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnCardChosenFromGraveyardEffect(true, filter,
                PutCards.HAND).concatBy("<br>"));
    }
    private UnderTheSkin(final UnderTheSkin card) {
        super(card);
    }

    @Override
    public UnderTheSkin copy() {
        return new UnderTheSkin(this);
    }
}
