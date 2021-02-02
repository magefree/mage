
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.IllusionToken;
import mage.target.TargetSpell;

/**
 *
 * @author North
 */
public final class SummonersBane extends CardImpl {

    public SummonersBane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_CREATURE));
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new IllusionToken()));
    }

    private SummonersBane(final SummonersBane card) {
        super(card);
    }

    @Override
    public SummonersBane copy() {
        return new SummonersBane(this);
    }
}
