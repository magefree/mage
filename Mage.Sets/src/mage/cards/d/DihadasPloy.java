package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.CardsDiscardedThisTurnCount;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.JumpStartAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.watchers.common.DiscardedCardWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DihadasPloy extends CardImpl {

    public DihadasPloy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{B}");

        // Draw two cards, then discard a card. You gain life equal to the number of cards you've discarded this turn.
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(2, 1));
        this.getSpellAbility().addEffect(new GainLifeEffect(CardsDiscardedThisTurnCount.PLURAL));
        this.getSpellAbility().addHint(CardsDiscardedThisTurnCount.getHint());
        this.getSpellAbility().addWatcher(new DiscardedCardWatcher());

        // Jump-start
        this.addAbility(new JumpStartAbility(this));
    }

    private DihadasPloy(final DihadasPloy card) {
        super(card);
    }

    @Override
    public DihadasPloy copy() {
        return new DihadasPloy(this);
    }
}
