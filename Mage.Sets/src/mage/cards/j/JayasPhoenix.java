package mage.cards.j;

import mage.MageInt;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyTargetStackAbilityEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackAbility;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class JayasPhoenix extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a planeswalker spell");

    static {
        filter.add(CardType.PLANESWALKER.getPredicate());
    }

    public JayasPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Jaya's Phoenix deals combat damage to a player or planeswalker, copy the next loyalty
        // ability you activate this turn when you activate it. You may choose new targets for the copy.
        this.addAbility(new DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility(
                new CreateDelayedTriggeredAbilityEffect(new JayasPhoenixTriggeredAbility()), false
        ));


        // Whenever you cast a planeswalker spell, you may return Jaya's Phoenix from your graveyard to the battlefield.
        this.addAbility(new SpellCastControllerTriggeredAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect()
                .setText("you may return {this} from your graveyard to the battlefield"), filter, true, false
        ));
    }

    private JayasPhoenix(final JayasPhoenix card) {
        super(card);
    }

    @Override
    public JayasPhoenix copy() {
        return new JayasPhoenix(this);
    }
}

class JayasPhoenixTriggeredAbility extends DelayedTriggeredAbility {

    public JayasPhoenixTriggeredAbility() {
        super(new CopyTargetStackAbilityEffect(), Duration.EndOfTurn);
    }

    public JayasPhoenixTriggeredAbility(final JayasPhoenixTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(this.getControllerId())) {
            return false;
        }

        StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
        if (stackAbility != null && stackAbility.getStackAbility() instanceof LoyaltyAbility) {
            this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
            return true;
        }

        return false;
    }

    @Override
    public JayasPhoenixTriggeredAbility copy() {
        return new JayasPhoenixTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "copy the next loyalty ability you activate this turn when you activate it. You may choose new targets for the copy";
    }
}
