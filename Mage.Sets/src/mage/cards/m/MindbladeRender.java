package mage.cards.m;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedBatchForOnePlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class MindbladeRender extends CardImpl {

    public MindbladeRender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.AZRA);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever your opponents are dealt combat damage, if any of that damage was dealt by a Warrior, you draw a card and you lose 1 life.
        this.addAbility(new MindbladeRenderTriggeredAbility());
    }

    private MindbladeRender(final MindbladeRender card) {
        super(card);
    }

    @Override
    public MindbladeRender copy() {
        return new MindbladeRender(this);
    }
}

class MindbladeRenderTriggeredAbility extends TriggeredAbilityImpl {

    public MindbladeRenderTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        this.addEffect(new LoseLifeSourceControllerEffect(1));
    }

    private MindbladeRenderTriggeredAbility(final MindbladeRenderTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public MindbladeRenderTriggeredAbility copy() {
        return new MindbladeRenderTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player controller = game.getPlayer(getControllerId());
        if (controller == null) {
            return false;
        }
        DamagedBatchForOnePlayerEvent dEvent = (DamagedBatchForOnePlayerEvent) event;

        if (!controller.hasOpponent(dEvent.getTargetId(), game)){
            return false;
        }
        if (!dEvent.isCombatDamage()) {
            return false;
        }

        int warriorDamage = dEvent.getEvents()
                .stream()
                .filter(ev -> {
                    Permanent attacker = game.getPermanentOrLKIBattlefield(ev.getSourceId());
                    return attacker != null && attacker.hasSubtype(SubType.WARRIOR, game);
                })
                .mapToInt(GameEvent::getAmount)
                .sum();

        return warriorDamage > 0;
    }

    @Override
    public String getRule() {
        return "Whenever your opponents are dealt combat damage, if any of that damage was dealt by a Warrior, you draw a card and you lose 1 life.";
    }
}
