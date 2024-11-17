package mage.cards.n;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;

/**
 *
 * @author Grath
 */
public final class NivMizzetVisionary extends CardImpl {

    public NivMizzetVisionary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You have no maximum hand size.
        Effect effect = new MaximumHandSizeControllerEffect(Integer.MAX_VALUE, Duration.WhileOnBattlefield, MaximumHandSizeControllerEffect.HandSizeModification.SET);
        this.addAbility(new SimpleStaticAbility(effect));

        // Whenever a source you control deals noncombat damage to an opponent, you draw that many cards.
        this.addAbility(new NivMizzetVisionaryAbility());
    }

    private NivMizzetVisionary(final NivMizzetVisionary card) {
        super(card);
    }

    @Override
    public NivMizzetVisionary copy() {
        return new NivMizzetVisionary(this);
    }
}

class NivMizzetVisionaryAbility extends TriggeredAbilityImpl {

    NivMizzetVisionaryAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(SavedDamageValue.MANY));
    }

    private NivMizzetVisionaryAbility(final NivMizzetVisionaryAbility ability) {
        super(ability);
    }

    @Override
    public NivMizzetVisionaryAbility copy() {
        return new NivMizzetVisionaryAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        if (damageEvent.isCombatDamage()
                || !game.getOpponents(controllerId).contains(event.getTargetId())
                || !Objects.equals(controllerId, game.getControllerId(event.getSourceId()))) {
            return false;
        }
        getAllEffects().setValue("damage", event.getAmount());
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a source you control deals noncombat damage to an opponent, you draw that many cards.";
    }
}
