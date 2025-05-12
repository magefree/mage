package mage.cards.l;

import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LaughingMad extends CardImpl {

    public LaughingMad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // As an additional cost to cast this spell, discard a card.
        this.getSpellAbility().addCost(new DiscardCardCost());

        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));

        // Flashback {3}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{3}{R}")));
    }

    private LaughingMad(final LaughingMad card) {
        super(card);
    }

    @Override
    public LaughingMad copy() {
        return new LaughingMad(this);
    }
}
