package mage.cards.r;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ResentfulRevelation extends CardImpl {

    public ResentfulRevelation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Look at the top three cards of your library. Put one of them into your hand and the rest into your graveyard.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                3, 1, PutCards.HAND, PutCards.GRAVEYARD
        ));

        // Flashback {6}{B}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{6}{B}")));
    }

    private ResentfulRevelation(final ResentfulRevelation card) {
        super(card);
    }

    @Override
    public ResentfulRevelation copy() {
        return new ResentfulRevelation(this);
    }
}
