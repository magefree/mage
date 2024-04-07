package mage.cards.r;

import mage.MageInt;
import mage.Mana;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RodeoPyromancers extends CardImpl {

    public RodeoPyromancers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever you cast your first spell each turn, add {R}{R}.
        this.addAbility(new RodeoPyromancersTriggeredAbility());
    }

    private RodeoPyromancers(final RodeoPyromancers card) {
        super(card);
    }

    @Override
    public RodeoPyromancers copy() {
        return new RodeoPyromancers(this);
    }
}

class RodeoPyromancersTriggeredAbility extends TriggeredAbilityImpl {

    RodeoPyromancersTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BasicManaEffect(Mana.RedMana(2)));
        setTriggerPhrase("Whenever you cast your first spell each turn, ");
    }

    private RodeoPyromancersTriggeredAbility(final RodeoPyromancersTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RodeoPyromancersTriggeredAbility copy() {
        return new RodeoPyromancersTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
            return watcher != null && watcher.getSpellsCastThisTurn(this.getControllerId()).size() == 1;
        }
        return false;
    }
}
