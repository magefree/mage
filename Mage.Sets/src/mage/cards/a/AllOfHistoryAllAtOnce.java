package mage.cards.a;

import mage.abilities.effects.common.counter.TimeTravelEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AllOfHistoryAllAtOnce extends CardImpl {

    public AllOfHistoryAllAtOnce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");

        // Time travel.
        this.getSpellAbility().addEffect(new TimeTravelEffect());

        // Storm
        this.addAbility(new StormAbility());
    }

    private AllOfHistoryAllAtOnce(final AllOfHistoryAllAtOnce card) {
        super(card);
    }

    @Override
    public AllOfHistoryAllAtOnce copy() {
        return new AllOfHistoryAllAtOnce(this);
    }
}
