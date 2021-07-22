package mage.cards.l;

import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LucidDreams extends CardImpl {

    public LucidDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        // Draw X cards, where X is the number of card types among cards in your graveyard.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(CardTypesInGraveyardCount.YOU)
                .setText("draw X cards, where X is the number of card types among cards in your graveyard"));
        this.getSpellAbility().addHint(CardTypesInGraveyardHint.YOU);
    }

    private LucidDreams(final LucidDreams card) {
        super(card);
    }

    @Override
    public LucidDreams copy() {
        return new LucidDreams(this);
    }
}
