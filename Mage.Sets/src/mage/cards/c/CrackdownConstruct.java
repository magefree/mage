
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackAbility;

/**
 *
 * @author spjspj
 */
public final class CrackdownConstruct extends CardImpl {

    public CrackdownConstruct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you activate an ability of an artifact or creature that isn't a mana ability, Crackdown Construct gets +1/+1 until end of turn.
        this.addAbility(new CrackdownConstructTriggeredAbility());
    }

    private CrackdownConstruct(final CrackdownConstruct card) {
        super(card);
    }

    @Override
    public CrackdownConstruct copy() {
        return new CrackdownConstruct(this);
    }
}

class CrackdownConstructTriggeredAbility extends TriggeredAbilityImpl {

    CrackdownConstructTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn), false);
        setTriggerPhrase("Whenever you activate an ability of an artifact or creature that isn't a mana ability, ");
    }

    CrackdownConstructTriggeredAbility(final CrackdownConstructTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CrackdownConstructTriggeredAbility copy() {
        return new CrackdownConstructTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(getControllerId())) {
            Card source = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (source != null && (source.isArtifact(game) || source.isCreature(game))) {
                StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
                if (!(stackAbility.getStackAbility() instanceof ActivatedManaAbilityImpl)) {
                    return true;
                }
            }
        }
        return false;
    }
}
