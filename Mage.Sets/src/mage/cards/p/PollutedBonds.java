
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class PollutedBonds extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("a land");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public PollutedBonds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        // Whenever a land enters the battlefield under an opponent's control, that player loses 2 life and you gain 2 life.
        Effect effect = new LoseLifeTargetEffect(2);
        effect.setText("that player loses 2 life");
        Ability ability = new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, effect, filter, false, SetTargetPointer.PLAYER, "");
        effect = new GainLifeEffect(2);
        effect.setText("and you gain 2 life");
        ability.addEffect(effect);
        this.addAbility(ability);

    }

    public PollutedBonds(final PollutedBonds card) {
        super(card);
    }

    @Override
    public PollutedBonds copy() {
        return new PollutedBonds(this);
    }
}
