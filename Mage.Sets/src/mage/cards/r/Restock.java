
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class Restock extends CardImpl {

    public Restock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");

        // Return two target cards from your graveyard to your hand. Exile Restock.
        Effect effect = new ReturnFromGraveyardToHandTargetEffect();
        effect.setText("Return two target cards from your graveyard to your hand");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(2, StaticFilters.FILTER_CARD_FROM_YOUR_GRAVEYARD));
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private Restock(final Restock card) {
        super(card);
    }

    @Override
    public Restock copy() {
        return new Restock(this);
    }
}
