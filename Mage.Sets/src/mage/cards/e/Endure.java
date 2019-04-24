
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.other.PlayerPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class Endure extends CardImpl {
    
    private static final FilterPermanentOrPlayer filter = new FilterPermanentOrPlayer("you and permanents you control");

    static {
        filter.getPermanentFilter().add(new ControllerPredicate(TargetController.YOU));
        filter.getPlayerFilter().add(new PlayerPredicate(TargetController.YOU));
    }

    public Endure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W}{W}");


        // Prevent all damage that would be dealt to you and permanents you control this turn.
        this.getSpellAbility().addEffect(new PreventAllDamageToAllEffect(Duration.EndOfTurn, filter));
        
    }

    public Endure(final Endure card) {
        super(card);
    }

    @Override
    public Endure copy() {
        return new Endure(this);
    }
}