
package mage.cards.f;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author L_J
 */
public final class ForethoughtAmulet extends CardImpl {

    public ForethoughtAmulet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // At the beginning of your upkeep, sacrifice Forethought Amulet unless you pay {3}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new GenericManaCost(3)), TargetController.YOU, false));

        // If an instant or sorcery source would deal 3 or more damage to you, it deals 2 damage to you instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ForethoughtAmuletEffect()));
    }

    private ForethoughtAmulet(final ForethoughtAmulet card) {
        super(card);
    }

    @Override
    public ForethoughtAmulet copy() {
        return new ForethoughtAmulet(this);
    }
}

class ForethoughtAmuletEffect extends ReplacementEffectImpl {

    public ForethoughtAmuletEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "If an instant or sorcery source would deal 3 or more damage to you, it deals 2 damage to you instead";
    }

    private ForethoughtAmuletEffect(final ForethoughtAmuletEffect effect) {
        super(effect);
    }

    @Override
    public ForethoughtAmuletEffect copy() {
        return new ForethoughtAmuletEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getAmount() >= 3) {
            MageObject object = game.getObject(event.getSourceId());
            return object != null && object.isInstantOrSorcery(game);
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getControllerId())) {
            event.setAmount(2);
        }
        return false;
    }
}
