
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LoneFox
 */
public final class DivineLight extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public DivineLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}");

        // Prevent all damage that would be dealt this turn to creatures you control.
        this.getSpellAbility().addEffect(new PreventAllDamageToAllEffect(Duration.EndOfTurn, filter)
                .setText("Prevent all damage that would be dealt this turn to creatures you control.")
        );
    }

    public DivineLight(final DivineLight card) {
        super(card);
    }

    @Override
    public DivineLight copy() {
        return new DivineLight(this);
    }
}
