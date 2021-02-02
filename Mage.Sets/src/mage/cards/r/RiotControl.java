

package mage.cards.r;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.PreventDamageToControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */


public final class RiotControl extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature your opponents control");
    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public RiotControl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");


        // Gain 1 life for each creature your opponents control. Prevent all damage that would be dealt to you this turn.
        this.getSpellAbility().addEffect(new GainLifeEffect(new PermanentsOnBattlefieldCount(filter)));
        this.getSpellAbility().addEffect(new PreventDamageToControllerEffect(Duration.EndOfTurn));

    }

    private RiotControl(final RiotControl card) {
        super(card);
    }

    @Override
    public RiotControl copy() {
        return new RiotControl(this);
    }

}
