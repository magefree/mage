package mage.cards.f;

import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FruitOfTizerus extends CardImpl {

    public FruitOfTizerus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Target player loses 2 life.
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Escape—{3}{B}, Exile three other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{3}{B}", 3));
    }

    private FruitOfTizerus(final FruitOfTizerus card) {
        super(card);
    }

    @Override
    public FruitOfTizerus copy() {
        return new FruitOfTizerus(this);
    }
}
