

package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Vengevine extends CardImpl {

    public Vengevine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        this.addAbility(HasteAbility.getInstance());
        this.addAbility(new VengevineAbility(), new VengevineWatcher());
    }

    private Vengevine(final Vengevine card) {
        super(card);
    }

    @Override
    public Vengevine copy() {
        return new Vengevine(this);
    }

}

class VengevineAbility extends TriggeredAbilityImpl {

    public VengevineAbility() {
        super(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(), true);
    }

    private VengevineAbility(final VengevineAbility ability) {
        super(ability);
    }

    @Override
    public VengevineAbility copy() {
        return new VengevineAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(controllerId)) {
            Watcher watcher = game.getState().getWatcher(VengevineWatcher.class, controllerId);
            if (watcher != null && watcher.conditionMet()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a spell, if it's the second creature spell you cast this turn, you may return {this} from your graveyard to the battlefield.";
    }

}


class VengevineWatcher extends Watcher {

    private int creatureSpellCount = 0;

    public VengevineWatcher() {
        super(WatcherScope.PLAYER);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        condition = false;
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getPlayerId().equals(controllerId)) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.isCreature(game)) {
                creatureSpellCount++;
                if (creatureSpellCount == 2)
                    condition = true;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        creatureSpellCount = 0;
    }

}
