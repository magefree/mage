
package mage.cards.f;

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
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class FurnaceOfRath extends CardImpl {

    public FurnaceOfRath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}{R}");

        // If a source would deal damage to a creature or player, it deals double that damage to that creature or player instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new FurnaceOfRathEffect()));
    }

    private FurnaceOfRath(final FurnaceOfRath card) {
        super(card);
    }

    @Override
    public FurnaceOfRath copy() {
        return new FurnaceOfRath(this);
    }
}

class FurnaceOfRathEffect extends ReplacementEffectImpl {

    public FurnaceOfRathEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If a source would deal damage to a permanent or player, it deals double that damage to that permanent or player instead";
    }

    public FurnaceOfRathEffect(final FurnaceOfRathEffect effect) {
        super(effect);
    }

    @Override
    public FurnaceOfRathEffect copy() {
        return new FurnaceOfRathEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PLAYER:
            case DAMAGE_PERMANENT:
                return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }
}
