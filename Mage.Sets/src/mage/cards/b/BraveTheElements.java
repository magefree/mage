

package mage.cards.b;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.continuous.GainProtectionFromColorAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class BraveTheElements extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("White creatures you control");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public BraveTheElements(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");


        // Choose a color. White creatures you control gain protection from the chosen color until end of turn.
        this.getSpellAbility().addEffect(new GainProtectionFromColorAllEffect(Duration.EndOfTurn, filter));
    }

    private BraveTheElements(final BraveTheElements card) {
        super(card);
    }

    @Override
    public BraveTheElements copy() {
        return new BraveTheElements(this);
    }

}
