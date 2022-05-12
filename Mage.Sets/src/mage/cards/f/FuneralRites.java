package mage.cards.f;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FuneralRites extends CardImpl {

    public FuneralRites(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // You draw two cards, lose 2 life, and put the top two cards of your library into your graveyard
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2)
                .setText("You draw two cards"));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2)
                .setText(", lose 2 life"));
        this.getSpellAbility().addEffect(new MillCardsControllerEffect(2)
                .concatBy(", then"));
    }

    private FuneralRites(final FuneralRites card) {
        super(card);
    }

    @Override
    public FuneralRites copy() {
        return new FuneralRites(this);
    }
}
