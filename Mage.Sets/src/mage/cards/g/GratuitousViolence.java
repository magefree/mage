
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author emerald000
 */
public final class GratuitousViolence extends CardImpl {

    public GratuitousViolence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}{R}");

        // If a creature you control would deal damage to a creature or player, it deals double that damage to that creature or player instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GratuitousViolenceReplacementEffect()));
    }

    private GratuitousViolence(final GratuitousViolence card) {
        super(card);
    }

    @Override
    public GratuitousViolence copy() {
        return new GratuitousViolence(this);
    }
}

class GratuitousViolenceReplacementEffect extends ReplacementEffectImpl {

    GratuitousViolenceReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If a creature you control would deal damage to a permanent or player, it deals double that damage to that creature or player instead";
    }

    GratuitousViolenceReplacementEffect(final GratuitousViolenceReplacementEffect effect) {
        super(effect);
    }

    @Override
    public GratuitousViolenceReplacementEffect copy() {
        return new GratuitousViolenceReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PLAYER:
            case DAMAGE_PERMANENT:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        return permanent != null
                && permanent.isCreature(game)
                && permanent.isControlledBy(source.getControllerId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }
}
