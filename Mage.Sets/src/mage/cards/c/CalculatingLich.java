package mage.cards.c;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CalculatingLich extends CardImpl {

    public CalculatingLich(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever a creature attacks one of your opponents, that player loses 1 life.
        this.addAbility(new CalculatingLichTriggeredAbility());
    }

    private CalculatingLich(final CalculatingLich card) {
        super(card);
    }

    @Override
    public CalculatingLich copy() {
        return new CalculatingLich(this);
    }
}

class CalculatingLichTriggeredAbility extends TriggeredAbilityImpl {

    CalculatingLichTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private CalculatingLichTriggeredAbility(final CalculatingLichTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player player = game.getPlayer(getControllerId());
        UUID defenderId = game.getCombat().getDefenderId(event.getSourceId());
        if (player == null || !player.hasOpponent(defenderId, game)) {
            return false;
        }
        getEffects().clear();
        addEffect(new LoseLifeTargetEffect(1).setTargetPointer(new FixedTarget(defenderId, game)));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a creature attacks one of your opponents, that player loses 1 life.";
    }

    @Override
    public CalculatingLichTriggeredAbility copy() {
        return new CalculatingLichTriggeredAbility(this);
    }
}
