package mage.cards.h;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackAbility;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class HarshMentor extends CardImpl {

    public HarshMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever an opponent activates an ability of an artifact, creature, or land on the battlefield, if it isn't a mana ability, Harsh Mentor deals 2 damage to that player.
        this.addAbility(new HarshMentorTriggeredAbility());
    }

    private HarshMentor(final HarshMentor card) {
        super(card);
    }

    @Override
    public HarshMentor copy() {
        return new HarshMentor(this);
    }
}

class HarshMentorTriggeredAbility extends TriggeredAbilityImpl {

    HarshMentorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(StaticValue.get(2), true, "that player", true));
    }

    private HarshMentorTriggeredAbility(final HarshMentorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HarshMentorTriggeredAbility copy() {
        return new HarshMentorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(getControllerId())) {
            Card source = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (source != null && (source.isArtifact(game) || source.isCreature(game) || source.isLand(game))) {
                StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
                if (!(stackAbility.getStackAbility() instanceof ActivatedManaAbilityImpl)) {
                    for (Effect effect : getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent activates an ability of an artifact, creature, or land on the battlefield, if it isn't a mana ability, {this} deals 2 damage to that player.";
    }
}
