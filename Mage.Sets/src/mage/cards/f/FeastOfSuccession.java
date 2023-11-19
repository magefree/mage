package mage.cards.f;

import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FeastOfSuccession extends CardImpl {

    public FeastOfSuccession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // All creatures get -4/-4 until end of turn. You become the monarch.
        this.getSpellAbility().addEffect(new BoostAllEffect(-4, -4, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new BecomesMonarchSourceEffect().setText("You become the monarch"));
        this.getSpellAbility().addHint(MonarchHint.instance);
    }

    private FeastOfSuccession(final FeastOfSuccession card) {
        super(card);
    }

    @Override
    public FeastOfSuccession copy() {
        return new FeastOfSuccession(this);
    }
}
