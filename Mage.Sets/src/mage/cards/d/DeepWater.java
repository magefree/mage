
package mage.cards.d;

import java.util.UUID;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ManaEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public final class DeepWater extends CardImpl {

    public DeepWater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{U}");

        // {U}: Until end of turn, if you tap a land you control for mana, it produces {U} instead of any other type.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DeepWaterReplacementEffect(), new ManaCostsImpl("{U}"));
        this.addAbility(ability);
    }

    public DeepWater(final DeepWater card) {
        super(card);
    }

    @Override
    public DeepWater copy() {
        return new DeepWater(this);
    }
}

class DeepWaterReplacementEffect extends ReplacementEffectImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    DeepWaterReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Neutral);
        staticText = "Until end of turn, if you tap a land you control for mana, it produces {U} instead of any other type";
    }

    DeepWaterReplacementEffect(final DeepWaterReplacementEffect effect) {
        super(effect);
    }

    @Override
    public DeepWaterReplacementEffect copy() {
        return new DeepWaterReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ManaEvent manaEvent = (ManaEvent) event;
        Mana mana = manaEvent.getMana();
        mana.setToMana(Mana.BlueMana(mana.count()));
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObject mageObject = game.getObject(event.getSourceId());
        if (mageObject != null && mageObject.isLand()) {
            Permanent land = game.getPermanent(event.getSourceId());
            return land != null && filter.match(land, game);
        }
        return false;
    }
}
