package mage.cards.o;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.combat.ChooseBlockersEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.common.ControlCombatRedundancyWatcher;

import java.util.UUID;

/**
 * @author noxx
 */
public final class OdricMasterTactician extends CardImpl {

    public OdricMasterTactician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever Odric, Master Tactician and at least three other creatures attack, you choose which creatures block this combat and how those creatures block.
        this.addAbility(new OdricMasterTacticianTriggeredAbility());
    }

    private OdricMasterTactician(final OdricMasterTactician card) {
        super(card);
    }

    @Override
    public OdricMasterTactician copy() {
        return new OdricMasterTactician(this);
    }
}

class OdricMasterTacticianTriggeredAbility extends TriggeredAbilityImpl {

    public OdricMasterTacticianTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ChooseBlockersEffect(Duration.EndOfCombat));
        this.addWatcher(new ControlCombatRedundancyWatcher());
    }

    public OdricMasterTacticianTriggeredAbility(final OdricMasterTacticianTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OdricMasterTacticianTriggeredAbility copy() {
        return new OdricMasterTacticianTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getCombat().getAttackers().size() >= 4 && game.getCombat().getAttackers().contains(this.sourceId);
    }
}
