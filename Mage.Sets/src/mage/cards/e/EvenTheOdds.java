
package mage.cards.e;

import java.util.UUID;
import mage.abilities.common.CastOnlyIfConditionIsTrueAbility;
import mage.abilities.condition.common.ControlsPermanentsComparedToOpponentsCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SoldierToken;

/**
 *
 * @author LevelX2
 */
public final class EvenTheOdds extends CardImpl {

    public EvenTheOdds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Cast Even the Odds only if you control fewer creatures than each opponent.
        this.addAbility(new CastOnlyIfConditionIsTrueAbility(new ControlsPermanentsComparedToOpponentsCondition(ComparisonType.FEWER_THAN, StaticFilters.FILTER_PERMANENT_CREATURES)));

        // Create three 1/1 white Soldier creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SoldierToken(), 3));
    }

    private EvenTheOdds(final EvenTheOdds card) {
        super(card);
    }

    @Override
    public EvenTheOdds copy() {
        return new EvenTheOdds(this);
    }
}
