
package mage.cards.r;

import java.util.UUID;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.common.AfterUpkeepStepCondtion;
import mage.abilities.condition.common.OnOpponentsTurnCondition;
import mage.abilities.effects.common.UntapAllLandsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author LevelX2
 */
public final class Reset extends CardImpl {

    public Reset(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}");

        // Cast Reset only during an opponent's turn after their upkeep step.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(null, null,
                new CompoundCondition(OnOpponentsTurnCondition.instance, AfterUpkeepStepCondtion.instance),
                "Cast this spell only during an opponent's turn after their upkeep step"));

        // Untap all lands you control.
        this.getSpellAbility().addEffect(new UntapAllLandsControllerEffect());
    }

    private Reset(final Reset card) {
        super(card);
    }

    @Override
    public Reset copy() {
        return new Reset(this);
    }
}
