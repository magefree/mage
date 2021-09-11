package mage.cards.e;

import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElectricRevelation extends CardImpl {

    public ElectricRevelation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // As an additional cost to cast this spell, discard a card.
        this.getSpellAbility().addCost(new DiscardCardCost());

        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));

        // Flashback {3}{R}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl<>("{3}{R}"), TimingRule.INSTANT));
    }

    private ElectricRevelation(final ElectricRevelation card) {
        super(card);
    }

    @Override
    public ElectricRevelation copy() {
        return new ElectricRevelation(this);
    }
}
