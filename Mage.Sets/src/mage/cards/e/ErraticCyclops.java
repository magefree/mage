package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 *
 * @author TheElk801
 */
public final class ErraticCyclops extends CardImpl {

    public ErraticCyclops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.CYCLOPS);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(8);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you cast an instant or sorcery spell, Erratic Cyclops gets +X/+0 until end of turn, where X is that spell's converted mana cost.
        this.addAbility(new ErraticCyclopsTriggeredAbility());
    }

    private ErraticCyclops(final ErraticCyclops card) {
        super(card);
    }

    @Override
    public ErraticCyclops copy() {
        return new ErraticCyclops(this);
    }
}

class ErraticCyclopsTriggeredAbility extends TriggeredAbilityImpl {

    public ErraticCyclopsTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private ErraticCyclopsTriggeredAbility(final ErraticCyclopsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && spell.isControlledBy(controllerId)
                && spell.isInstantOrSorcery(game)) {
            this.getEffects().clear();
            this.addEffect(new BoostSourceEffect(
                    spell.getManaValue(), 0, Duration.EndOfTurn
            ));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast an instant or sorcery spell, "
                + "{this} gets +X/+0 until end of turn, "
                + "where X is that spell's mana value.";
    }

    @Override
    public ErraticCyclopsTriggeredAbility copy() {
        return new ErraticCyclopsTriggeredAbility(this);
    }
}
