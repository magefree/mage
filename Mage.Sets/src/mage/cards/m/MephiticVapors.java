package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author TheElk801
 */
public final class MephiticVapors extends CardImpl {

    public MephiticVapors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // All creatures get -1/-1 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(-1, -1, Duration.EndOfTurn));

        // Surveil 2.
        this.getSpellAbility().addEffect(new SurveilEffect(2).concatBy("<br>"));
    }

    private MephiticVapors(final MephiticVapors card) {
        super(card);
    }

    @Override
    public MephiticVapors copy() {
        return new MephiticVapors(this);
    }
}
