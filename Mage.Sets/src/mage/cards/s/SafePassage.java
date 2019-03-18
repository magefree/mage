

package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreatureOrPlayer;
import mage.filter.predicate.other.PlayerPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class SafePassage  extends CardImpl {

    private static final FilterCreatureOrPlayer filter = new FilterCreatureOrPlayer("you and creatures you control");

    static {
        filter.getCreatureFilter().add(new ControllerPredicate(TargetController.YOU));
        filter.getPlayerFilter().add(new PlayerPredicate(TargetController.YOU));
    }

    public SafePassage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        this.getSpellAbility().addEffect(new PreventAllDamageToAllEffect(Duration.EndOfTurn, filter));
    }

    public SafePassage(final SafePassage card) {
        super(card);
    }

    @Override
    public SafePassage copy() {
        return new SafePassage(this);
    }

}
