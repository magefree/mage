
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.UndauntedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class SeedsOfRenewal extends CardImpl {

    public SeedsOfRenewal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{G}");

        // Undaunted
        this.addAbility(new UndauntedAbility());
        // Return up to two target cards from your graveyard to your hand. Exile Seeds of Renewal.
        Effect effect = new ReturnFromGraveyardToHandTargetEffect();
        effect.setText("Return up to two target cards from your graveyard to your hand");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 2, StaticFilters.FILTER_CARD_FROM_YOUR_GRAVEYARD));
        this.getSpellAbility().addEffect(new ExileSpellEffect());

    }

    private SeedsOfRenewal(final SeedsOfRenewal card) {
        super(card);
    }

    @Override
    public SeedsOfRenewal copy() {
        return new SeedsOfRenewal(this);
    }
}
