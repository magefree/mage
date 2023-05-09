package mage.cards.f;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FiremaneCommando extends CardImpl {

    public FiremaneCommando(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you attack with two or more creatures, draw a card.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new DrawCardSourceControllerEffect(1), 2
        ));

        // Whenever another player attacks with two or more creatures, they draw a card if none of those creatures attacked you.
        this.addAbility(new FiremaneCommandoTriggeredAbility());
    }

    private FiremaneCommando(final FiremaneCommando card) {
        super(card);
    }

    @Override
    public FiremaneCommando copy() {
        return new FiremaneCommando(this);
    }
}

class FiremaneCommandoTriggeredAbility extends TriggeredAbilityImpl {

    FiremaneCommandoTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardTargetEffect(SavedDamageValue.MUCH));
    }

    private FiremaneCommandoTriggeredAbility(final FiremaneCommandoTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FiremaneCommandoTriggeredAbility copy() {
        return new FiremaneCommandoTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (this.getControllerId().equals(event.getPlayerId()) || game.getCombat().getAttackers().size() < 2) {
            return false;
        }
        boolean youWereAttacked = game
                .getCombat()
                .getGroups()
                .stream()
                .map(CombatGroup::getDefenderId)
                .filter(Objects::nonNull)
                .anyMatch(this.getControllerId()::equals);
        this.getEffects().setValue("damage", youWereAttacked ? 0 : 1);
        this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever another player attacks with two or more creatures, " +
                "they draw a card if none of those creatures attacked you.";
    }
}
