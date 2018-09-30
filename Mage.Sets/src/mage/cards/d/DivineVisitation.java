package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CopyEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.AngelVigilanceToken;

/**
 *
 * @author TheElk801
 */
public final class DivineVisitation extends CardImpl {

    public DivineVisitation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}");

        // TODO: This implementation is not entirely correct, see https://twitter.com/EliShffrn/status/1042145606582591490
        // If one or more creature tokens would be created under your control, that many 4/4 white Angel creature tokens with flying and vigilance are created instead.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD, new DivineVisitationEffect()
        ));
    }

    public DivineVisitation(final DivineVisitation card) {
        super(card);
    }

    @Override
    public DivineVisitation copy() {
        return new DivineVisitation(this);
    }
}

class DivineVisitationEffect extends ReplacementEffectImpl {

    public DivineVisitationEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Copy, false);
        staticText = "If one or more creature tokens would be created "
                + "under your control, that many 4/4 white Angel creature "
                + "tokens with flying and vigilance are created instead.";
    }

    public DivineVisitationEffect(DivineVisitationEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent perm = ((EntersTheBattlefieldEvent) event).getTarget();
        return perm != null
                && perm.isCreature()
                && perm instanceof PermanentToken
                && perm.isControlledBy(source.getControllerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        game.addEffect(new CopyEffect(Duration.Custom, new AngelVigilanceToken(), event.getTargetId()), source);
        return false;
    }

    @Override
    public DivineVisitationEffect copy() {
        return new DivineVisitationEffect(this);
    }

}
