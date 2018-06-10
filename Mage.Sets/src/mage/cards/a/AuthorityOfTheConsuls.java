
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author fireshoes
 */
public final class AuthorityOfTheConsuls extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public AuthorityOfTheConsuls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");

        // Creatures your opponents control enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AuthorityOfTheConsulsTapEffect()));

        // Whenever a creature enters the battlefield under an opponent's control, you gain 1 life.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new GainLifeEffect(1), filter,
                "Whenever a creature enters the battlefield under an opponent's control, you gain 1 life."));
    }

    public AuthorityOfTheConsuls(final AuthorityOfTheConsuls card) {
        super(card);
    }

    @Override
    public AuthorityOfTheConsuls copy() {
        return new AuthorityOfTheConsuls(this);
    }
}

class AuthorityOfTheConsulsTapEffect extends ReplacementEffectImpl {

    AuthorityOfTheConsulsTapEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Tap);
        staticText = "Creatures your opponents control enter the battlefield tapped";
    }

    AuthorityOfTheConsulsTapEffect(final AuthorityOfTheConsulsTapEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        if (target != null) {
            target.setTapped(true);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
            if (permanent != null && permanent.isCreature()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public AuthorityOfTheConsulsTapEffect copy() {
        return new AuthorityOfTheConsulsTapEffect(this);
    }
}
